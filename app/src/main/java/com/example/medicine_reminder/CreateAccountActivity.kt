package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.model.SignUpRequest
import com.example.medicine_reminder.model.SignUpResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import com.example.medicine_reminder.ui.RegisterScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : ComponentActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {

        val gso = com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
            com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken(getString(R.string.default_web_client_id)) // from google-services.json
            .requestEmail().build()

        // 2. Build a GoogleSignInClient with the options
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                RegisterScreen(
                    onBackClick = { TODO() },
                    onRegisterClick = { name, email, password, confirmPassword ->
                        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT)
                                .show()
                        }

                        if (password != confirmPassword) {
                            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                        }

                        val request = SignUpRequest(name, email, password)

                        RetrofitClient.api.signUp(request)
                            .enqueue(object : Callback<SignUpResponse> {
                                override fun onResponse(
                                    call: Call<SignUpResponse>,
                                    response: Response<SignUpResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val message = response.body()?.message
                                            ?: "User registered successfully"
                                        Toast.makeText(
                                            this@CreateAccountActivity,
                                            message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        if (message == "User registered successfully") {
                                            startActivity(
                                                Intent(this@CreateAccountActivity,
                                                    LoginPageActivity::class.java)
                                            )
                                            finish()
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@CreateAccountActivity,
                                            "Signup failed",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }

                                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                                    Log.e("SignUpError", "API call failed", t)
                                    Toast.makeText(
                                        this@CreateAccountActivity,
                                        "Network error: ${t.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    },
                    onSignWithGoogleClick = {
                        val signInIntent = mGoogleSignInClient.signInIntent
                        startActivityForResult(signInIntent, RC_SIGN_IN)
                    },
                    onLogInClick = {
                        startActivity(
                            Intent(this@CreateAccountActivity, LoginPageActivity::class.java)
                        )
                        finish()
                    }
                )
            }
        }
    }
}