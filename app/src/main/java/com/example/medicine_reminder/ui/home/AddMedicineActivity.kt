package com.example.medicine_reminder.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddMedicineActivity : ComponentActivity() {

    private val medicineViewModel: MedicineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                AddMedicineScreen(
                    onBackClick = { finish() },
                    makeScheduleClick = { medicineName, strength, type, amount, startDate, endDate,reminders ->


                        val medicine = Medicine(
                            name = medicineName,
                            strength = strength,
                            type = type,
                            amount = amount,
                            startDate = startDate,
                            finishDate = endDate,
                        )

                        medicineViewModel.addMedicine(
                            medicine,reminders
                        )

                        Toast.makeText(
                            this@AddMedicineActivity,
                            "Medicine added successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish() // Optional: close activity after adding
                    }
                )
            }
        }
    }
}
