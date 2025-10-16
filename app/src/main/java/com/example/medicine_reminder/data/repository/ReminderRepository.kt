package com.example.medicine_reminder.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.medicine_reminder.reminder.AlarmService
import com.example.medicine_reminder.reminder.ReminderReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("ScheduleExactAlarm")
    fun scheduleSnooze(reminderId: Int, medicineName: String) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val snoozeAt = System.currentTimeMillis() + 10 * 60 * 1000L

        val pi = PendingIntent.getBroadcast(
            context,
            reminderId,
            Intent(context, ReminderReceiver::class.java).apply {
               action = AlarmService.ACTION_FIRE
               putExtra("reminderId", reminderId)
               putExtra("medicineName", medicineName)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, snoozeAt, pi)
        NotificationManagerCompat.from(context).cancel(reminderId)
    }

    fun dismiss(reminderId: Int) {
        NotificationManagerCompat.from(context).cancel(reminderId)
    }
}
