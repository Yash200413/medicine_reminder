package com.example.medicine_reminder.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.forgotpassword.ChangePasswordActivity
import com.example.medicine_reminder.ui.otp.OtpVerificationPage
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.OtpVerificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpVerificationActivity : ComponentActivity() {

    private val viewModel: OtpVerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val email = intent.getStringExtra("email") ?: ""

        setContent {
            MedicineReminderTheme {
                OtpVerificationPage(
                    onBackClick = { finish() },
                    onVerifyClick = { otp ->
                        viewModel.verifyOtp(
                            email,
                            otp,
                            onSuccess = {
                                startActivity(
                                    Intent(
                                        this,
                                        ChangePasswordActivity::class.java
                                    ).putExtra("email", email)
                                )
                                finish()
                            },
                            onError = { errorMsg ->
                                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                )
            }
        }
    }
}
