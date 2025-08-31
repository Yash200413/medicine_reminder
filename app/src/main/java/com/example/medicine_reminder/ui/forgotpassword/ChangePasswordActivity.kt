package com.example.medicine_reminder.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.login.LoginPageActivity
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.ChangePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : ComponentActivity() {

    private val viewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val email = intent.getStringExtra("email") ?: ""

        setContent {
            MedicineReminderTheme {
                ChangePasswordScreen(
                    onBackClick = { finish() },
                    onChangePasswordClick = { newPassword, confirmPassword ->
                        viewModel.changePassword(
                            email = email,
                            newPassword = newPassword,
                            confirmPassword = confirmPassword,
                            onSuccess = {
                                startActivity(Intent(this, LoginPageActivity::class.java))
                                finish()
                            },
                            onError = { msg ->
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            }
        }
    }
}
