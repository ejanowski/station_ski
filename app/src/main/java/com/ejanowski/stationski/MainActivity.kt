package com.ejanowski.stationski

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ejanowski.stationski.dataclass.DataBaseHelper
import com.ejanowski.stationski.dataclass.Difficulty
import com.ejanowski.stationski.dataclass.Slope
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
                    Column(Modifier.fillMaxWidth()) {
                        var tabIndex by remember { mutableStateOf(0) }
                        val tabs = listOf("Pistes", "Remontées")
                        TabRow(selectedTabIndex = tabIndex) {
                            tabs.forEachIndexed { index, s ->
                                Tab(selected = tabIndex == index,
                                    onClick = { tabIndex = index },
                                    text = { Text(s) })
                            }
                        }
                        when(tabIndex) {
                            0 -> HomeSlope()
                            1 -> HomeLift()
                        }
                    }
                }
            }
        }
    }
}
