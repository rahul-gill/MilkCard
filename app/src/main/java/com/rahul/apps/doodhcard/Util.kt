package com.rahul.apps.doodhcard

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

    fun getDateTimeString(calendar: Calendar = Calendar.getInstance()): String {
        val sdf = SimpleDateFormat("MMMdd", Locale.getDefault())
        val sdf1 = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        val sdf2 = SimpleDateFormat("dd/M/yyyy", Locale.getDefault())
        val midDay = sdf1.parse(sdf2.format(calendar.time) + " 12:00:00")

        val session =
            if (calendar.time.after(midDay)) "E"
            else "M"
        return sdf.format(calendar.time) + ":" + session
    }

    fun compareDateTimeStrings(a: String, b: String): Int{
        val orderMapping = hashMapOf<CharSequence, Int>(
            "Jan" to 0,
            "Feb" to 1,
            "Mar" to 2,
            "Apr" to 3,
            "May" to 4,
            "Jun" to 5,
            "Jul" to 6,
            "Aug" to 7,
            "Sep" to 8,
            "Oct" to 9,
            "Nov" to 10,
            "Dec" to 11,
        )
        val (aPart1, bPart1, aPart2, bPart2) = listOf(
            a.subSequence(0,3), b.subSequence(0,3), a.subSequence(3,5), b.subSequence(3,5)
        )
        val  (aPart3 , bPart3) = a[5] to b[5]
        return when{
            orderMapping[aPart1]!! > orderMapping[bPart1]!! -> 1
            orderMapping[aPart1]!! < orderMapping[bPart1]!! -> -1
            aPart2.toString().toInt() > bPart2.toString().toInt() -> 1
            aPart2.toString().toInt() < bPart2.toString().toInt() -> -1
            aPart3 == 'E' && bPart3 == 'M' -> 1
            aPart3 == 'M' && bPart3 == 'E' -> -1
            else -> 0
        }


    }
}