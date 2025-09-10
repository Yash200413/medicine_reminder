package com.example.medicine_reminder.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder
import com.example.medicine_reminder.utils.TimeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleReminder(med: Medicine, rem: Reminder) {
        scheduleNextIfInWindow(med, rem)
    }

    fun cancelReminder(reminderId: Int) {
        val pi = pendingIntent(reminderId, -1, reminderId, null)
        alarmManager.cancel(pi)
    }

    fun rescheduleAll(medsWithReminders: List<Pair<Medicine, Reminder>>) {
        medsWithReminders.forEach { (m, r) -> scheduleNextIfInWindow(m, r) }
    }

    // 🔑 Ensure reminders only trigger inside medicine start/finish window
    private fun scheduleNextIfInWindow(med: Medicine, rem: Reminder) {
        val now = System.currentTimeMillis()
        if (!TimeUtils.isWithinWindow(now, med.startDate, med.finishDate)) return

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
    }

    // 🔑 Create PendingIntent that always fires ACTION_FIRE → ReminderReceiver → AlarmActivity
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
