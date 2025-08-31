package com.example.medicine_reminder.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.forgotpassword.ForgotPasswordScreen
import com.example.medicine_reminder.ui.otp.OtpVerificationActivity
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : ComponentActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                ForgotPasswordScreen(
                    onResetClick = { email ->
                        viewModel.sendOtp(
                            email = email,
                            onSuccess = {
                                startActivity(
                                    Intent(
                                        this,
                                        OtpVerificationActivity::class.java
                                    ).putExtra("email", email)
                                )
                                finish()
                            },
                            onError = { message ->
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    onBackClick = { finish() }
                )
            }
        }
    }
}
