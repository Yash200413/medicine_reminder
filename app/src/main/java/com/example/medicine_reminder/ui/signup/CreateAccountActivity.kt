package com.example.medicine_reminder.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.medicine_reminder.ui.home.HomeActivity
import com.example.medicine_reminder.ui.login.LoginPageActivity
import com.example.medicine_reminder.viewmodel.CreateAccountViewModel
import com.example.medicine_reminder.ui.signup.RegisterScreen
import com.example.medicine_reminder.ui.theme.MedicineReminderTheme
import com.example.medicine_reminder.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountActivity : ComponentActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100
    private val viewModel: CreateAccountViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Google Sign-In Options
        // ✅ Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(com.example.medicine_reminder.R.string.default_web_client_id)) // From google-services.json
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        enableEdgeToEdge()
        setContent {
            MedicineReminderTheme {
                RegisterScreen(
                    onBackClick = { finish() },
                    onRegisterClick = { name, email, password, confirmPassword ->
                        viewModel.signUp(
                            name = name,
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            onSuccess = {
                                startActivity(Intent(this, LoginPageActivity::class.java))
                                finish()
                            },
                            onError = { error ->
                                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    onSignWithGoogleClick = {
                        val signInIntent = mGoogleSignInClient.signInIntent
                        startActivityForResult(signInIntent, RC_SIGN_IN)
                    },
                    onLogInClick = {
                        startActivity(Intent(this, LoginPageActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }

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
}
