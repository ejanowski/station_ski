package com.ejanowski.stationski

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DataBaseHelper {
    companion object {
        val database = Firebase.database("https://station-ski-default-rtdb.europe-west1.firebasedatabase.app/")
    }
}