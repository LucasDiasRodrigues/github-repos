package com.rodrigues.githubrepositories.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val DATE_TIME_API_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val DATE_DISPLAY_FORMAT = "yyyy/MM/dd"

    fun formatDateFromString(date: String): String {
        try {
            return formatFromDate(parseFromString(date, DATE_TIME_API_FORMAT), DATE_DISPLAY_FORMAT)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun formatFromDate(date: Date?, displayFormat: String?): String {
        val simpleDateFormat = SimpleDateFormat(displayFormat, Locale.ROOT)
        return date?.let { simpleDateFormat.format(date) } ?: ""
    }

    fun parseFromString(date: String, displayFormat: String): Date {
        val simpleDateFormat = SimpleDateFormat(displayFormat, Locale.ROOT)
        return try {
            simpleDateFormat.parse(date) ?: Date()
        } catch (exception: ParseException) {
            exception.printStackTrace()
            Date()
        }
    }
}