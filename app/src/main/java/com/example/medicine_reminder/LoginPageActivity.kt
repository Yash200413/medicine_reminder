package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.model.LoginResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import com.example.medicine_reminder.ui.LoginScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPageActivity : ComponentActivity() {

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
                LoginScreen(onLoginClick = { email, password ->
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            this, "Please enter both email and password", Toast.LENGTH_SHORT
                        ).show()
                        return@LoginScreen
                    }

                    val request = LoginRequest(email, password)
                    RetrofitClient.api.login(request).enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>, response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                startActivity(
                                    Intent(
                                        this@LoginPageActivity, HomeActivity::class.java
                                    )
                                )
                                finish()
                            } else {
                                Toast.makeText(
                                    this@LoginPageActivity,
                                    "Invalid credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Log.e("LoginError", "Network error", t)
                            Toast.makeText(
                                this@LoginPageActivity,
                                "Network error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }, onSignInWithGoogleClick = {
                    val signInIntent = mGoogleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }, onForgotPasswordClick = {
                    startActivity(
                        Intent(
                            this@LoginPageActivity, ForgetPasswordPage::class.java
                        )
                    )
                    finish()
                })
            }
        }
    }

    // Google Sign-in result
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
                verifyGoogleToken(idToken)
            }
        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "Sign-in failed, code: ${e.statusCode}")
            Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyGoogleToken(idToken: String) {
        val request = GoogleLoginRequest(idToken)
        RetrofitClient.api.googleLogin(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    startActivity(Intent(this@LoginPageActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginPageActivity, "Google login failed", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginPageActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}