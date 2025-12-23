package com.example.medicine_reminder

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MedicineReminderApp : Application() {


    override fun onCreate() {
//        Log.d("AppTime", "Application onCreate: ${System.currentTimeMillis()}")
        super.onCreate()

        // Run channel creation in a background thread to avoid ANR
        CoroutineScope(Dispatchers.Default).launch {
            createReminderNotificationChannel()
        }
    }

    private fun createReminderNotificationChannel() {
        try {
            val channelId = "reminder_channel"
            val channelName = "Medicine Reminders"
            val description = "Reminders for taking your medicines"

//            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//            val attributes = AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_ALARM)
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .build()

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                this.description = description
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableVibration(true)
                enableLights(true)
//                setSound(soundUri, attributes)
            }

            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (nm.getNotificationChannel(channelId) == null) {
                nm.createNotificationChannel(channel)
                Log.d("MedicineReminderApp", "Notification channel created.")
            } else {
                Log.d("MedicineReminderApp", "Notification channel already exists.")
            }
        } catch (e: Exception) {
            Log.e("MedicineReminderApp", "Error creating channel: ${e.message}")
        }
    }
}



