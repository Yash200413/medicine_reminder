package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.model.LoginResponse
import com.example.medicine_reminder.model.OtpRequest
import com.example.medicine_reminder.model.OtpResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import com.example.medicine_reminder.ui.ForgotPasswordScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                ForgotPasswordScreen(
                    onResetClick = { email ->
                        val request = OtpRequest(email)
                        RetrofitClient.api.sendOtp(request).enqueue(object : Callback<OtpResponse> {
                            override fun onResponse(
                                call: Call<OtpResponse>,
                                response: Response<OtpResponse>
                            ) {
                                if (response.isSuccessful && response.body() != null) {
                                    startActivity(
                                        Intent(
                                            this@ForgetPasswordPage,
                                            OtpVerificationActivity::class.java
                                        )
                                    )
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@ForgetPasswordPage,
                                        "Invalid email address",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                                Log.e("LoginError", "Network error", t)
                                Toast.makeText(
                                    this@ForgetPasswordPage,
                                    "Network error: ${t.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    },
                    onBackClick = { }
                )
                }
            }
        }
    }

