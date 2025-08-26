package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.model.ChangePasswordRequest
import com.example.medicine_reminder.model.ChangePasswordResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import com.example.medicine_reminder.ui.ChangePasswordScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val email = intent.getStringExtra("email") ?: ""
        setContent {
            MedicineReminderTheme {
                ChangePasswordScreen(
                    onBackClick = { TODO() },
                    onChangePasswordClick = { newPassword, confirmPassword ->
                        if (newPassword == confirmPassword) {
                            val request = ChangePasswordRequest(email, newPassword)
                            RetrofitClient.api.changePassword(request).enqueue(object :
                                Callback<ChangePasswordResponse> {
                                override fun onResponse(
                                    call: Call<ChangePasswordResponse?>,
                                    response: Response<ChangePasswordResponse?>
                                ) {
                                    if (response.isSuccessful) {
                                        val result = response.body()
                                        if (result != null && result.message){
                                            startActivity(
                                                Intent(
                                                    this@ChangePasswordActivity,
                                                    LoginPageActivity::class.java
                                                )
                                            )
                                            finish()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@ChangePasswordActivity,
                                            "Error Retry",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }

                                override fun onFailure(
                                    call: Call<ChangePasswordResponse?>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@ChangePasswordActivity,
                                        "Server error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })
                        } else {
                            Toast.makeText(
                                this@ChangePasswordActivity,
                                "Error! Confirm Password Not Match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
    }
}