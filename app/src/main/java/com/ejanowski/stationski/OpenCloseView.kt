package com.ejanowski.stationski

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ejanowski.stationski.dataclass.Slope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable fun OpenClose(slope: Slope) {
    val backgroundColor = if(slope.status) { Color.Green } else { Color.Red }
    Box(modifier = Modifier
        .border(1.dp, colorResource(id = R.color.black), RoundedCornerShape(CornerSize(8.dp)))
        .clip(RoundedCornerShape(CornerSize(8.dp)))
        .background(backgroundColor)
        .clickable {
            val newValue = !slope.status
            Firebase.database.reference.child("slopes/${slope.index}/status").setValue(newValue)
            slope.status = newValue
        }
    ){
        Row(Modifier.padding(8.dp)) {
            Text(if(slope.status) { "Ouverte" } else { "Fermée" })
        }
    }
}