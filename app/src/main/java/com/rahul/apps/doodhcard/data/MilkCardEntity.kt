package com.rahul.apps.doodhcard.data

import androidx.room.Entity
import com.rahul.apps.doodhcard.Util

const val MILK_CARD_TABLE = "milk_card"

@Entity(
    tableName = MILK_CARD_TABLE,
    primaryKeys = ["datetime"]
)
data class MilkCardEntity(
    val datetime: String = Util.getDateTimeString(),
    val weight: Double = 0.0,
    val fat: Double = 0.0,
    val rate: Double = 0.0
)