package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

fun getTodaysDate():String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Calendar.getInstance().time)

}