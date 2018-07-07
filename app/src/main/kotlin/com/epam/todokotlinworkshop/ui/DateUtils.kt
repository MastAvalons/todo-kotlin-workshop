package com.epam.todokotlinworkshop.ui

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    private val date = SimpleDateFormat("EEE, dd MMMM, yyyy ")
    private val time = SimpleDateFormat("hh:mm")

    private const val FORMAT = "mm.dd  hh:mm"
    private const val FORMAT_SHORT = "hh:mm"

    fun isToday(date: Date): Boolean {
        return DateUtils.isToday(date.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date): String {
        val format = if (isToday(date)) FORMAT_SHORT else FORMAT
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

    fun getDate(dateTime: Date): String {
        return date.format(dateTime)
    }

    fun getTime(dateTime: Date): String {
        return time.format(dateTime)
    }

    fun getTime(hours: Int, minutes: Int): String {
        return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes)
    }
}