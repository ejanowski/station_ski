package com.ejanowski.stationski.dataclass

import com.ejanowski.stationski.R

data class Lift(val name: String = "",
                val status: Boolean = false,
                val type: String = "telesiege") {

}

enum class LiftType {
    CHAIR, SKI;

    fun drawableId(): Int {
        return when(this) {
            CHAIR -> R.drawable.chairlift
            SKI -> R.drawable.skilift
        }
    }

    companion object {
        fun from(type: String): LiftType {
            return when(type) {
                "telesiege" -> CHAIR
                "teleski" -> SKI
                else -> SKI
            }
        }
    }
}