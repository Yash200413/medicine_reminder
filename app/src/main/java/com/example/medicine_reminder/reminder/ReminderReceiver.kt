package com.example.medicine_reminder.reminder

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import com.example.medicine_reminder.data.local.Repository
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
    lateinit var reminderRepository: ReminderRepository

    @Inject
    lateinit var scheduler: ReminderScheduler

    @Inject
    lateinit var repository: Repository

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MedicineReminder:WakeLock")
        wl.acquire(10_000L) // hold for 10s

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val reminderId = intent.getIntExtra("reminderId", 0)
                val medicineName = intent.getStringExtra("medicineName") ?: "your medicine"

                if (intent.action == ACTION_FIRE) {
                    // Only launch AlarmActivity if screen is off / device idle
                    if (isPhoneLocked(context) || isDeviceIdle(context)) {
                        Log.d("receiver","onReceiver called")

                        // Check if activity is already running
                        val launchIntent = Intent(context, AlarmActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            putExtra("reminderId", reminderId)
                            putExtra("medicineName", medicineName)
                        }
                        context.startActivity(launchIntent)
                    } else {
                        // Device active → just notify & play alarm sound
                        NotificationHelper.showAlarmNotification(context, reminderId, medicineName)
                        alarmPlayer.startAlarm()
                    }
                }



                when(intent.action) {
                    ACTION_SNOOZE -> {
                        reminderRepository.scheduleSnooze(reminderId, medicineName)
                        alarmPlayer.stopAlarm()
                    }
                    ACTION_DISMISS -> {
                        val reminder = repository.getReminderById(reminderId) ?: return@launch
                        val medicine = repository.getMedicineById(reminder.medicineOwnerId)
                        if (medicine != null) {
                            scheduler.rescheduleReminderForNextDay(medicine, reminder)
                            reminderRepository.dismiss(reminderId)
                        }
                        alarmPlayer.stopAlarm()
                    }
                }
            } finally {
                wl.release()
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




