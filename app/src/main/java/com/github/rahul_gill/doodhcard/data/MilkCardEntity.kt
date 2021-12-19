package com.github.rahul_gill.doodhcard.data

import androidx.room.Entity
import java.util.*

const val MILK_CARD_TABLE = "milk_card"

@Entity(
    tableName = MILK_CARD_TABLE,
    primaryKeys = ["datetime"]
)
data class MilkCardEntity(
    val datetime: Date = Calendar.getInstance().time,
    val weight: Double = 0.0,
    val fat: Double = 0.0,
    val rate: Double = 0.0
){
    val price: Double
        get() = weight * rate
}