package com.rahul.apps.doodhcard

import java.text.SimpleDateFormat
import java.util.*

fun formattedDouble(value: Double): String{
    return "%.2f".format(value)
}


fun stringToDouble(value: String): Double{
    return if(value.isBlank()) 0.0
    else value.toDouble()
}

fun getDateTimeString(calendar: Calendar = Calendar.getInstance()): String{
    val sdf = SimpleDateFormat("MMMdd", Locale.getDefault())
    val sdf1 = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
    val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
    val midDay = sdf1.parse(sdf2.format(calendar.time) + " 12:00:00")
    val session =
        if(calendar.time.after(midDay)) "E"
        else "M"
    return sdf.format(calendar.time) + ":" + session
}