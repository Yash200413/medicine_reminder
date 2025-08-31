package com.example.medicine_reminder.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                MedicineReminderScreen(
                    onMoreClick = {
                        startActivity(
                            Intent(this@HomeActivity, AddMedicineActivity::class.java)
                        )
                        finish()
                    }
//                    onLogoutClick = {
//                        viewModel.logout {
//                            startActivity(Intent(this, LoginPageActivity::class.java))
//                            finish()
//                        }
//                    }
                )
            }
        }
    }
}
