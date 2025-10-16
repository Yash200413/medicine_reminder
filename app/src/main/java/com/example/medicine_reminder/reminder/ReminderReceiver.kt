package com.example.medicine_reminder.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val now = System.currentTimeMillis()
        Log.d("ReceiverTime", "onReceive called at: $now")
        // Start service immediately, offload work from BroadcastReceiver
        CoroutineScope(Dispatchers.IO).launch {
            val serviceIntent = Intent(context, AlarmService::class.java).apply {
                action = intent.action
                putExtras(intent)
            }

            context.startForegroundService(serviceIntent)
        }
    }
}