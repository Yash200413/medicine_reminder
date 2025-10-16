package com.example.medicine_reminder.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.medicine_reminder.R
import com.example.medicine_reminder.reminder.AlarmService
import com.example.medicine_reminder.reminder.ReminderReceiver
import com.example.medicine_reminder.ui.alarm.AlarmActivity

object NotificationHelper {


    private const val CHANNEL_ID = "alarm_foreground_channel"
    private const val CHANNEL_NAME = "Alarm Foreground Service"
    private const val CHANNEL_DESC = "Used to keep the alarm service alive in the background"

    /**
     * Creates (if necessary) and returns a Notification for ForegroundService.
     */
    fun createForegroundNotification(context: Context): Notification {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW // Foreground service, not an alert
        ).apply {
            description = CHANNEL_DESC
            enableVibration(false)
        }

        notificationManager.createNotificationChannel(channel)

        // Build notification
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Medicine Reminder Running")
            .setContentText("Your scheduled alarms will ring on time.")
            .setSmallIcon(R.drawable.ic_alarm) // make sure this exists in res/drawable
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }


    @SuppressLint("MissingPermission", "FullScreenIntentPolicy")
    fun showAlarmNotification(context: Context, reminderId: Int, medicineName: String) {
        val dismissPI = actionBroadcastPI(context, reminderId, AlarmService.ACTION_DISMISS)
        val snoozePI = actionBroadcastPI(context, reminderId, AlarmService.ACTION_SNOOZE)

        val notification = NotificationCompat.Builder(context, "reminder_channel")
            .setSmallIcon(R.drawable.ic_launcher)
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
            .setTimeoutAfter(1000*2*60)
            .addAction(R.drawable.ic_launcher_foreground, "Snooze", snoozePI)
            .addAction(R.drawable.ic_launcher_foreground, "Dismiss", dismissPI)
            .setOngoing(true)                           // Stays visible until user acts
            .setAutoCancel(false)
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
