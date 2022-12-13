package com.zero.pengusirburung.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(day: Int): Date {
    return Calendar.getInstance().apply { add(Calendar.DATE, day) }.time
}

fun String.formatDate(): String {
    var format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val newDate: Date? = format.parse(this)
    format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(newDate!!)
}

fun Date.formatSimpleDate(): String {
    val formatter = SimpleDateFormat("dd-MM")
    return formatter.format(this)
}

fun Date.formatDateTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return formatter.format(this)
}

fun Date.formatDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(this)
}