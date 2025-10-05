package com.example.medicine_reminder.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    /**
     * Returns true if [now] is between [startDate] and [finishDate].
     */
    fun isWithinWindow(now: Long, startDate: Long, finishDate: Long): Boolean {
        return now in startDate..finishDate
    }

    /**
     * Calculates the next alarm trigger time for the given [timeString] ("HH:mm").
     * If the time today has already passed, it returns the same time tomorrow.
     */
    fun nextTriggerForTodayOrTomorrow(timeString: String, now: Long): Long {
        val cal = Calendar.getInstance().apply {
            timeInMillis = now
            val parts = timeString.split(":")
            set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            set(Calendar.MINUTE, parts[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // If this time today has already passed, schedule for tomorrow
        if (cal.timeInMillis <= now) {
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }

        return cal.timeInMillis
    }

    /**
     * Returns the trigger time for tomorrow at the same HH:mm.
     */
    fun nextTriggerForTomorrow(timeString: String): Long {
        val cal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1) // move to next day
            val parts = timeString.split(":")
            set(Calendar.HOUR_OF_DAY, parts[0].toInt())
            set(Calendar.MINUTE, parts[1].toInt())
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.timeInMillis
    }

    /**
     * Returns a trigger time for a specific day given by [baseDayMillis] and [time] ("HH:mm").
     */
    fun nextTriggerForSpecificDay(time: String, baseDayMillis: Long): Long {
        val parts = time.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()

        return Calendar.getInstance().apply {
            timeInMillis = baseDayMillis
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    /**
     * Utility to format millis into a readable date-time string (for debugging/logs).
     */
    fun formatMillis(millis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(millis))
    }
}
