package com.example.medicine_reminder.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.data.entity.Medicine
import com.example.medicine_reminder.ui.home.AddMedicineScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddMedicineActivity : ComponentActivity() {

    data class FrequencyOption(
        val label: String,
        val hours: Int?
    )

    private val medicineViewModel: MedicineViewModel by viewModels()

    private val frequencyOptions = listOf(
        FrequencyOption("Once a day", 24),
        FrequencyOption("Twice a day", 12),
        FrequencyOption("Thrice a day", 8),
        FrequencyOption("Every 6 hours", 6),
        FrequencyOption("Every 8 hours", 8),
        FrequencyOption("Every 12 hours", 12),
        FrequencyOption("As needed", null)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                AddMedicineScreen(
                    frequencyOptions = frequencyOptions,
                    onBackClick = { finish() },
                    makeScheduleClick = { medicineName, strength, whenToTake, type, amount, frequency, startDate, endDate, selectedDays ->

                        val reminderTimeMillis = getReminderMillis(whenToTake)
                        val repeatPattern = selectedDays.joinToString(",")

                        val medicine = Medicine(
                            name = medicineName,
                            strength = strength,
                            dosage = amount,
                            type = type,
                            startDate = startDate,
                            endDate = endDate,
                            pattern = repeatPattern,
                            time = reminderTimeMillis
                        )

                        medicineViewModel.addMedicine(medicine)

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

    private fun getReminderTime(option: String): Pair<Int, Int>? {
        return when (option) {
            "Before breakfast" -> 7 to 30
            "After breakfast" -> 9 to 0
            "Before lunch" -> 12 to 0
            "After lunch" -> 14 to 0
            "Before dinner" -> 19 to 0
            "After dinner" -> 20 to 30
            "At bedtime" -> 22 to 0
            "Morning" -> 8 to 0
            "Afternoon" -> 13 to 0
            "Evening" -> 18 to 0
            "Night" -> 21 to 0
            "Empty stomach" -> 6 to 30
            else -> null
        }
    }

    private fun getReminderMillis(option: String): Long? {
        val time = getReminderTime(option) ?: return null
        val (hour, minute) = time

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }
}
