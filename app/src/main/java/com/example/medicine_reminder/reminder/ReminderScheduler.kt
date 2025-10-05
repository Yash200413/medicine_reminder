package com.example.medicine_reminder.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder
import com.example.medicine_reminder.utils.TimeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Schedule a reminder if the medicine is still in its valid window.
     */
    fun scheduleReminder(med: Medicine, rem: Reminder) {
        scheduleNextIfInWindow(med, rem)
    }

    /**
     * Cancel a reminder by ID.
     */
    fun cancelReminder(reminderId: Int) {
        val pi = pendingIntent(reminderId, -1, reminderId, null)
        alarmManager.cancel(pi)
    }

    /**
     * Ensures reminders only trigger inside medicine start/finish window.
     */
    fun scheduleNextIfInWindow(med: Medicine, rem: Reminder) {
        val now = System.currentTimeMillis()
        if (!TimeUtils.isWithinWindow(now, med.startDate, med.finishDate)) return

        // Cancel any existing pending intent
        cancelReminder(rem.reminderId)

        val triggerAt = TimeUtils.nextTriggerForTodayOrTomorrow(rem.time, now)

        val pi = pendingIntent(
            requestCode = rem.reminderId,
            medicineId = med.medicineId,
            reminderId = rem.reminderId,
            medicineName = med.name
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAt,
            pi
        )
        Log.d("reminder","reminder scheduled1")
    }

    fun rescheduleReminderForNextDay(med: Medicine, rem: Reminder) {
        // Compute next day trigger
        val nextDay = System.currentTimeMillis() + 24 * 60 * 60 * 1000 // +1 day in millis
        if (nextDay !in med.startDate..med.finishDate) return // outside course, do nothing

        val triggerAt = TimeUtils.nextTriggerForTomorrow(rem.time) // your util function

        cancelReminder(rem.reminderId)

        val pi = pendingIntent(
            requestCode = rem.reminderId,
            medicineId = med.medicineId,
            reminderId = rem.reminderId,
            medicineName = med.name
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAt,
            pi
        )
        Log.d("reminder","reminder scheduled2")
    }


    /**
     * Create PendingIntent that always fires ACTION_FIRE → ReminderReceiver → AlarmActivity
     */
    private fun pendingIntent(
        requestCode: Int,
        medicineId: Int,
        reminderId: Int,
        medicineName: String?
    ): PendingIntent {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            action = ReminderReceiver.ACTION_FIRE
            putExtra("medicineId", medicineId)
            putExtra("reminderId", reminderId)
            putExtra("medicineName", medicineName)
        }
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
