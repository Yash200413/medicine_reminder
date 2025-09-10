package com.example.medicine_reminder.ui.alarm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.medicine_reminder.reminder.ReminderReceiver
import com.example.medicine_reminder.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {

    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reminderId = intent.getIntExtra("reminderId", 0)
        val medicineName = intent.getStringExtra("medicineName") ?: "your medicine"

        viewModel.startAlarm()

        setContent {
            AlarmScreen(
                medicineName = medicineName,
                onDismiss = { viewModel.dismiss(reminderId); finish() },
                onSnooze = { viewModel.snooze(reminderId, medicineName); finish() }
            )
        }
    }
}

