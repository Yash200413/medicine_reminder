package com.example.medicine_reminder

import NavAppHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MedicineReminderTheme {

                // Make status bar white
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.White,
                        darkIcons = true   // makes battery/time/icons visible
                    )
                }

                NavAppHost()
            }
        }
    }
}
