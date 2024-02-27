package com.ejanowski.stationski.dataclass

import com.ejanowski.stationski.R

class Slope(var name: String = "", var level: Int = 0, var status: Boolean = true, var index: Int = 0)

enum class Difficulty {
    GREEN, BLUE, RED, BLACK;

    fun colorId(): Int {
        return when(this) {
            GREEN -> R.color.difficulty_1
            BLUE -> R.color.difficulty_2
            RED -> R.color.difficulty_3
            BLACK -> R.color.difficulty_4
        }
    }

    companion object {
        fun from(level: Int): Difficulty {
            return when(level) {
                1 -> GREEN
                2 -> BLUE
                3 -> RED
                4 -> BLACK
                else -> GREEN
            }
        }
    }
}