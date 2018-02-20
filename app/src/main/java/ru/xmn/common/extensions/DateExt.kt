package ru.xmn.common.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.stampInSeconds(): Long = this.time / 1000

fun Date.dayName(): String {
    val df = SimpleDateFormat("EEEE")
    return df.format(date)
}


