package com.ejanowski.stationski

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.ejanowski.stationski.dataclass.Lift
import com.ejanowski.stationski.ui.theme.StationSkiTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
R.color.black
        setContent {
            StationSkiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun GetData(lifts: SnapshotStateList<Lift>) {
    DataBaseHelper.database.getReference("lifts")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val _lifts = snapshot.children.mapNotNull { it.getValue(Lift::class.java) }
                Log.d("database", lifts.toString())
                lifts.addAll(_lifts)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("dataBase", error.toString())
            }
        })
}

@Composable
fun Greeting() {
    val lifts = remember {
        mutableStateListOf<Lift>()
    }
    LazyColumn {
        items(lifts.toList()) {
            Text(it.name)
        }
    }
    GetData(lifts)
}