package com.example.medicine_reminder.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.signup.CreateAccountActivity
import com.example.medicine_reminder.ui.forgotpassword.ForgotPasswordActivity
import com.example.medicine_reminder.ui.home.HomeActivity
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginPageActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(com.example.medicine_reminder.R.string.default_web_client_id)) // From google-services.json
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        checkForJwtAndLogin()

        setContent {
            MedicineReminderTheme {
                LoginScreen(
                    onRegisterClick = {
                        startActivity(Intent(this, CreateAccountActivity::class.java))
                        finish()
//                        startActivity(Intent(this, HomeActivity::class.java))
//                       finish()
                    },
                    onLoginClick = { email, password ->
                        loginViewModel.login(
                            email = email,
                            password = password,
                            onSuccess = {
                                startActivity(Intent(this, HomeActivity::class.java))
                                finish()
                            },
                            onError = { msg ->
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    onSignInWithGoogleClick = {
                        val signInIntent = mGoogleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    },
                    onForgotPasswordClick = {
                        startActivity(Intent(this, ForgotPasswordActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }

    // ✅ Modern activity result launcher for Google sign-in
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val email = account.email
            val idToken = account.idToken

            if (idToken != null) {
                loginViewModel.googleLogin(
                    email = email,
                    idToken = idToken,
                    onSuccess = {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    },
                    onError = { msg ->
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Google login failed: No ID token", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Log.e("GoogleLogin", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Google login failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkForJwtAndLogin() {
        loginViewModel.checkForJwtAndLogin(
            onValid = {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            },
            onInvalid = {
                TokenManager(this).clearToken()
            },
            onError = { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
