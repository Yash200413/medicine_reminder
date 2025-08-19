package com.example.medicine_reminder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.ui.OtpVerificationPage
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme

class OtpVerification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                OtpVerificationPage(
                    onBackClick = {
                    }
                )
            }
        }
    }
}

