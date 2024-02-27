package com.ejanowski.stationski

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToSlopes()
        }

        setContent {
            val mailValue = remember { mutableStateOf(TextFieldValue("")) }
            val passwordValue = remember { mutableStateOf(TextFieldValue("")) }
            Column(
                Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = mailValue.value,
                    onValueChange = {
                    mailValue.value = it
                },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = { Text("Mail") },)
                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = {
                    passwordValue.value = it
                },
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = { Text("Password") })
                OutlinedButton(onClick = { connection(mailValue.value.text, passwordValue.value.text) }) {
                    Text("Se connecter")
                }
                TextButton(onClick = { register(mailValue.value.text, passwordValue.value.text) }) {
                    Text("S'inscrire")
                }
            }
        }
    }

    fun connection(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToSlopes()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToSlopes()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("auth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun goToSlopes() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}