package com.example.medicine_reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicine_reminder.data.entity.Medicine
import com.example.medicine_reminder.data.entity.Reminder
import com.example.medicine_reminder.ui.AddMedicineScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.MedicineViewModel

class AddMedicineActivity : ComponentActivity() {

    private val medicineViewModel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                AddMedicineScreen(onBackClick = {},
                    makeScheduleClick = { medicineName, strength, whenToTake, type, amount, frequency, startDate, endDate, selectedDays ->
                        val repeatPattern = selectedDays.joinToString(",")
//                        val reminder = Reminder(
//                            medicineId = medicineId,
//                            time = reminderTime,
//                            repeatPattern = repeatPattern,  // "Mon,Wed,Fri"
//                            isActive = true
//                        )
//                        val medicine = Medicine(
//                            name = medicineName,
//                            strength = strength,
//                            dosage = dosage,
//                            type = type,
//                            startDate = startDate,
//                            endDate = endDate
//                        )
//                        medicineViewModel.addMedicine(medicine, reminders)
                    }
                )
            }
        }
    }
}
