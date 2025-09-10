package com.example.medicine_reminder.ui.home

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.ui.alarm.AlarmActivity
import com.example.medicine_reminder.ui.login.LoginPageActivity
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.utils.AlarmPermissionHelper
import com.example.medicine_reminder.viewmodel.HomeViewModel
import com.example.medicine_reminder.viewmodel.MedicineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {



    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifications allowed ✅", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications denied ❌", Toast.LENGTH_SHORT).show()
            }
        }



    private val viewModel: HomeViewModel by viewModels()

    private val medicineViewModel: MedicineViewModel by viewModels()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ✅ Request POST_NOTIFICATIONS (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // ✅ Ask user for exact alarm (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }

        AlarmPermissionHelper.checkAndRequestPermissions(this)



        enableEdgeToEdge()
        setContent {
            val medicinesFromDb = medicineViewModel.medicines.collectAsState(
                initial = emptyList() // use empty list as initial value
            ).value

            // Map DB entity to UI model
            val uiMedicines = medicinesFromDb.map { entity ->
                Medicine(
                    name = entity.medicine.name,
                    amount = entity.medicine.amount,
                    taken = false,
                    strength = entity.medicine.strength,
                    type = entity.medicine.type,
                    reminders = entity.reminders.map { it.time }
                )
            }

            MedicineReminderTheme {
                MedicineReminderScreen(
                    medicineList = uiMedicines,
                    onMenuOptionSelected = { selectedOption ->
                        when (selectedOption) {
                            "Add Medicine" -> {
                                startActivity(Intent(this, AddMedicineActivity::class.java))
                            }
                            "Setting"->{
//                                startActivity(Intent(this, AlarmActivity::class.java))
                            }
                            "Logout" -> {
                               viewModel.logout(
                                   onLoggedOut ={ startActivity(Intent(this, LoginPageActivity::class.java))}
                               )
                            }
                        }
                    },
                    onMoreClick = {
                    }
                )
            }
        }
    }

}
