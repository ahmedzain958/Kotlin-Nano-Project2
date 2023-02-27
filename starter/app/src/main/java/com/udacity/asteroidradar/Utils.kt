package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun getTodaysDate():String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Calendar.getInstance().time)
}

fun getDateAfterWeek():String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 7)
    val dateAfter7Days = calendar.time
    return dateFormat.format(dateAfter7Days)
}