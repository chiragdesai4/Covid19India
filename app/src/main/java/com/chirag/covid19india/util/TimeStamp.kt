package com.chirag.covid19india.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeStamp {
    fun dateToSeconds(givenDateString: String?): Long {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
            val mDate = sdf.parse(givenDateString)
            return TimeUnit.MILLISECONDS.toSeconds(mDate.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun dMMMhhmma(secondsTime: Long): String {
        val tz = TimeZone.getTimeZone(TimeZone.getDefault().id)
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeZone = tz
        cal.timeInMillis = secondsTime * 1000L
        return SimpleDateFormat("d MMM, HH:mm", Locale.ENGLISH).format(cal.time)
    }
}