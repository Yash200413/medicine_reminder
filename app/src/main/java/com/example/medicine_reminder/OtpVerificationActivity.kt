package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.model.VerifyOtp
import com.example.medicine_reminder.model.VerifyOtpResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import com.example.medicine_reminder.ui.OtpVerificationPage
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                OtpVerificationPage(
                    onBackClick = { TODO() },
                    onVerifyClick = { otp ->
                        var request = VerifyOtp(otp)
                        RetrofitClient.api.verifyOtp(request)
                            .enqueue(object : Callback<VerifyOtpResponse> {
                                override fun onResponse(
                                    call: Call<VerifyOtpResponse?>,
                                    response: Response<VerifyOtpResponse?>
                                ) {
                                    if (response.isSuccessful && response.body() != null) {
                                        startActivity(
                                            Intent(
                                                this@OtpVerificationActivity,
                                                ChangePasswordActivity::class.java
                                            )
                                        )
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@OtpVerificationActivity,
                                            "Invalid Text",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(
                                    call: Call<VerifyOtpResponse?>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@OtpVerificationActivity,
                                        "Network error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })
                    }
                )
            }
        }
    }
}