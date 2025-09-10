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
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject lateinit var repository: Repository
    @Inject lateinit var scheduler: ReminderScheduler

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.getAllMedicinesWithReminders().collect { medsWithReminders ->
                    val pairs = mutableListOf<Pair<Medicine, Reminder>>()

                    // Normal List.forEach (no flow import needed)
                    medsWithReminders.forEach { mw ->
                        mw.reminders.forEach { r ->
                            pairs.add(mw.medicine to r)
                        }
                    }

                    scheduler.rescheduleAll(pairs)
                }
            }
        }
    }
}

