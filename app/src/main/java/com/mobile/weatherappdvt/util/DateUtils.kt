package com.mobile.weatherappdvt.util

import java.text.SimpleDateFormat
import java.util.*

private const val API_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
private const val COMPARE_DATE_FORMAT = "yyyy-MM-dd"
private const val APP_FORMAT_DAY = "E"

object DateUtils {

    fun getDayForDate(dateString: String?): String? {
        if (dateString == null || dateString.isEmpty()) {
            return null
        }

        val format = SimpleDateFormat(API_DATE_FORMAT, Locale.US)
        val d = format.parse(dateString)
        format.applyPattern(APP_FORMAT_DAY)

        return getFullDay(format, d)
    }

    private fun getFullDay(format: SimpleDateFormat, d: Date?): String? {
        if (d == null) {
            return null
        }

        // TODO: Add to strings.xml
        return when (format.format(d)) {
            "Mon" -> "Monday"
            "Tue" -> "Tuesday"
            "Wed" -> "Wednesday"
            "Thu" -> "Thursday"
            "Fri" -> "Friday"
            "Sat" -> "Saturday"
            "Sun" -> "Sunday"
            else -> null
        }
    }

    fun isNotToday(dateString: String?): Boolean {
        if(dateString == null) {
            return false
        }

        val format = SimpleDateFormat(COMPARE_DATE_FORMAT, Locale.US)
        val currentDate = Date(System.currentTimeMillis())
        val previousDate = format.parse(dateString) ?: return false
        val firstDate = format.format(currentDate)
        val secondDate = format.format(previousDate)
        return firstDate != secondDate
    }
}