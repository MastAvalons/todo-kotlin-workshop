package com.epam.todokotlinworkshop.ui

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

// Java-like utils
object DateUtils {

    private val date = SimpleDateFormat("EEE, dd MMMM, yyyy ")
    private val time = SimpleDateFormat("hh:mm")

    fun getDate(dateTime: Date): String {
        return date.format(dateTime)
    }

    fun getTime(dateTime: Date): String {
        return time.format(dateTime)
    }
}

// Kotlin-like utils
const val FORMAT = "MM.dd  hh:mm"
const val FORMAT_SHORT = "hh:mm"

val Date.isToday get() = DateUtils.isToday(time)

val Date.formatted: String
    get () {
        val format = if (this.isToday) FORMAT_SHORT else FORMAT
        return SimpleDateFormat(format).format(this)
    }