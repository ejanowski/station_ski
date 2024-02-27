package com.ejanowski.stationski

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ejanowski.stationski.dataclass.DataBaseHelper
import com.ejanowski.stationski.dataclass.Difficulty
import com.ejanowski.stationski.dataclass.Lift
import com.ejanowski.stationski.dataclass.LiftType
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@Composable
fun HomeLift() {
    val lifts = remember {
        mutableStateListOf<Lift>()
    }
    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        items(lifts.toList()) {
            LiftView(it)
        }
    }
    GetData(lifts)
}
@Composable
fun LiftView(lift: Lift) {
    val type = LiftType.from(lift.type)
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Image(painterResource(type.drawableId()), "")
        Text(lift.name)
    }
}

@Composable
fun GetData(lifts: SnapshotStateList<Lift>) {
    DataBaseHelper.database.getReference("lifts")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fireBaseLifts = snapshot.children.mapNotNull { it.getValue(Lift::class.java) }
                lifts.addAll(fireBaseLifts)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("dataBase", error.toString())
            }
        })
}
