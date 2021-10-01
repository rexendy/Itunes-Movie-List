package com.test.movieapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
    private  val currentDate = Date()

    fun returnCurrentDate(): String {
        return simpleDateFormat.format(currentDate)
    }
}