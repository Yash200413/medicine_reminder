package com.example.medicine_reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.ui.MedicineReminderScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                MedicineReminderScreen()
            }
        }
    }
}