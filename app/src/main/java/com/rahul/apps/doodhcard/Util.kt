package com.rahul.apps.doodhcard

fun showFormattedDouble(value: Double): String{
    return "%.2f".format(value)
}


fun stringToDouble(value: String): Double{
    return if(value.isBlank()) 0.0
    else value.toDouble()
}