package com.rahul.apps.doodhcard

import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class ItemModel(
    val weight: Double = 0.0,
    val fat: Double = 0.0,
    val rate: Double = 0.0,
    val datetime: MEDate = MEDate(Calendar.getInstance())
){
    val price: Double
        get() = rate * weight
    val formattedWeight: String
        get() = showFormattedDouble(weight)
    val formattedFat: String
        get() = showFormattedDouble(fat)
    val formattedRate: String
        get() = showFormattedDouble(rate)
    val formattedPrice: String
        get() = showFormattedDouble(price)
    private fun showFormattedDouble(value: Double): String{
        return "%.3f".format(value)
    }
}
@JsonClass(generateAdapter = true)
class MEDate(dateTime: Calendar = Calendar.getInstance()){
    var session = "M"
    var date : Date = Calendar.getInstance().time
    init {
        val sdf1 = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        val midDay = sdf1.parse(sdf2.format(dateTime.time) + " 12:00:00")
        session =
            if(dateTime.time.after(midDay)) "E"
            else "M"
        date = Calendar.getInstance().time
    }
}