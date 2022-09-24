package com.crypto.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Int) : String{
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    val date = Date(timestamp.toLong() * 1000)
//dd-MM-yyyy
    return sdf.format(date)
}
