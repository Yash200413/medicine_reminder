package com.example.medicine_reminder.ui.alarm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.medicine_reminder.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {

    private val viewModel: AlarmViewModel by viewModels()
    private val TAG = "AlarmActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val reminderId = intent.getIntExtra("reminderId", 0)
            val medicineName = intent.getStringExtra("medicineName") ?: "your medicine"

            Log.d(TAG, "AlarmActivity started for reminderId=$reminderId, medicineName=$medicineName")

            Log.d(TAG, "Alarm started successfully")

            setContent {
                AlarmScreen(
                    medicineName = medicineName,
                    onDismiss = {
                        try {
                            viewModel.dismiss(reminderId)
                            Log.d(TAG, "Alarm dismissed for reminderId=$reminderId")
                        } catch (e: Exception) {
                            Log.e(TAG, "Error dismissing alarm", e)
                        }
                        finish()
                    },
                    onSnooze = {
                        try {
                            viewModel.snooze(reminderId, medicineName)
                            Log.d(TAG, "Alarm snoozed for reminderId=$reminderId")
                        } catch (e: Exception) {
                            Log.e(TAG, "Error snoozing alarm", e)
                        }
                        finish()
                    }
                )
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error in AlarmActivity onCreate", e)
            finish() // close activity if something goes wrong
        }
    }
}
