package com.ejanowski.stationski

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.ejanowski.stationski.dataclass.DataBaseHelper
import com.ejanowski.stationski.dataclass.Difficulty
import com.ejanowski.stationski.dataclass.Slope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DisplayedDifficulty(val name: String, val difficulty: Difficulty?)
@Composable fun HomeSlope() {
    val slopes = remember {
        mutableStateListOf<Slope>()
    }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    val menuDifficulties = listOf(
        DisplayedDifficulty("toutes", null),
        DisplayedDifficulty("Vertes", Difficulty.GREEN),
        DisplayedDifficulty("Bleus", Difficulty.BLUE),
        DisplayedDifficulty("Rouges", Difficulty.RED),
        DisplayedDifficulty("Noires", Difficulty.BLACK)
    )

    var difficulty = remember {
        mutableStateOf<DisplayedDifficulty>(menuDifficulties.first())
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = { expanded = true }) {
            Text("Filtrer : ${difficulty.value.name}")
        }

        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false }) {
            menuDifficulties.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = { difficulty.value = item }
                )
            }
        }
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        items(slopes.toList().filter {
            val d = Difficulty.from(it.level)
            difficulty.value.difficulty?.let {
                it == d
            } ?: run {
                true
            }
        }) {
            SlopeView(it)
        }
    }
    GetData(slopes)
}
@Composable
fun SlopeView(slope: Slope) {
    val difficulty = Difficulty.from(slope.level)
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(colorResource(difficulty.colorId()))
                    .padding(vertical = 8.dp)
            )
            Text(slope.name)
            Spacer(Modifier.weight(1f))
            OpenClose(slope)
        }
    }
}

@Composable
fun GetData(slopes: SnapshotStateList<Slope>) {
    DataBaseHelper.database.getReference("slopes")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var index = 0
                val fireBaseSlopes = snapshot.children.mapNotNull {
                    val slope = it.getValue(Slope::class.java)
                    slope?.index = index
                    index += 1
                    return@mapNotNull slope
                }
                slopes.removeAll { true }
                slopes.addAll(fireBaseSlopes)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("dataBase", error.toString())
            }
        })
}
