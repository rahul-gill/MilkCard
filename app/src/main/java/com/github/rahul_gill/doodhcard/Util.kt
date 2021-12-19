package com.github.rahul_gill.doodhcard

import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun formattedDouble(value: Double?, dashed: Boolean = true): String {
        if(value == null || value == 0.0) {
            return if(dashed) "-"
            else ""
        }
        return "%.2f".format(value)
    }


    fun stringToDouble(value: String): Double {
        return if (value.isBlank()) 0.0
        else value.toDouble()
    }

    fun getDateTimeString(date: Date = Calendar.getInstance().time): String {
        val sdf = SimpleDateFormat("MMMdd", Locale.getDefault())
        val sdf1 = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        val midDay = sdf1.parse(sdf2.format(date) + " 12:00:00")

        val session =
            if (date.after(midDay)) "E"
            else "M"
        return sdf.format(date) + ":" + session
    }
    fun getDateTimeString(calendar: Calendar = Calendar.getInstance()): String{
        return getDateTimeString(calendar.time)
    }
}