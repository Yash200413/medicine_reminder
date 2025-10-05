package com.example.medicine_reminder.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.medicine_reminder.data.local.Repository

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var repository: Repository
    @Inject lateinit var scheduler: ReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                // ✅ Fetch all reminders from DB
                val reminders = repository.getAllReminders()

                // For each reminder, fetch its medicine and reschedule
                reminders.forEach { rem ->
                    val med = repository.getMedicineById(rem.medicineOwnerId)
                    if (med != null) {
                        // ✅ Pass both medicine and reminder
                        scheduler.scheduleReminder(med, rem)
                    }
                }
            }
        }
    }
}
