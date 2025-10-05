package com.example.medicine_reminder.utils

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.medicine_reminder.R
import com.example.medicine_reminder.reminder.ReminderReceiver
import com.example.medicine_reminder.ui.alarm.AlarmActivity

object NotificationHelper {

    @SuppressLint("MissingPermission", "FullScreenIntentPolicy")
    fun showAlarmNotification(context: Context, reminderId: Int, medicineName: String) {
        val dismissPI = actionBroadcastPI(context, reminderId, ReminderReceiver.ACTION_DISMISS)
        val snoozePI = actionBroadcastPI(context, reminderId, ReminderReceiver.ACTION_SNOOZE)

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Medicine Reminder")
            .setContentText("Time to take $medicineName")
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(0, 1000, 500, 1000))
            .setFullScreenIntent(
                PendingIntent.getActivity(
                    context,
                    reminderId,
                    Intent(context, AlarmActivity::class.java).apply {
                        putExtra("reminderId", reminderId)
                        putExtra("medicineName", medicineName)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                ),
                true
            )
            .addAction(R.drawable.ic_launcher_foreground, "Snooze", snoozePI)
            .addAction(R.drawable.ic_launcher_foreground, "Dismiss", dismissPI)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(reminderId, notification)
    }

    private fun actionBroadcastPI(
        context: Context,
        reminderId: Int,
        action: String
    ): PendingIntent {
        val i = Intent(context, ReminderReceiver::class.java).apply {
            this.action = action
            putExtra("reminderId", reminderId)
        }
        return PendingIntent.getBroadcast(
            context,
            reminderId + action.hashCode(),
            i,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
