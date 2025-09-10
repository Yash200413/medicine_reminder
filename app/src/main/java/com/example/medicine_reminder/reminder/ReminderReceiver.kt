package com.example.medicine_reminder.reminder

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import com.example.medicine_reminder.utils.AlarmPlayer
import com.example.medicine_reminder.data.repository.ReminderRepository
import com.example.medicine_reminder.ui.alarm.AlarmActivity
import com.example.medicine_reminder.utils.NotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmPlayer: AlarmPlayer

    @Inject
    lateinit var repository: ReminderRepository

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reminderId = intent.getIntExtra("reminderId", 0)
                val medicineName = intent.getStringExtra("medicineName") ?: "your medicine"

                when (intent.action) {
                    ACTION_FIRE -> {
                        if (isPhoneLocked(context) || isDeviceIdle(context)) {
                            val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            }
                            context.startActivity(alarmIntent)

                        } else {
                            alarmPlayer.startAlarm()
                            NotificationHelper.showAlarmNotification(
                                context,
                                reminderId,
                                medicineName
                            )
                        }
                    }

                    ACTION_SNOOZE -> {
                        repository.scheduleSnooze(reminderId, medicineName)
                        alarmPlayer.stopAlarm()
                    }

                    ACTION_DISMISS -> {
                        repository.dismiss(reminderId)
                        alarmPlayer.stopAlarm()
                    }
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    companion object {
        const val ACTION_FIRE = "com.example.medicine_reminder.ACTION_FIRE"
        const val ACTION_SNOOZE = "com.example.medicine_reminder.ACTION_SNOOZE"
        const val ACTION_DISMISS = "com.example.medicine_reminder.ACTION_DISMISS"
    }

    fun isPhoneLocked(context: Context): Boolean {
        val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        val isLocked = keyguardManager.isKeyguardLocked // true if locked (PIN, pattern, etc.)
        val isScreenOn =
            powerManager.isInteractive // true if screen is on

        return isLocked || !isScreenOn
    }

    fun isDeviceIdle(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isDeviceIdleMode
    }
}




