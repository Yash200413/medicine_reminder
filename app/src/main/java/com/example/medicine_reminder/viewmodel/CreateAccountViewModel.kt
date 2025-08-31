package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.SignUpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    fun signUp(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            onError("All fields are required")
            return
        }

        if (password != confirmPassword) {
            onError("Passwords do not match")
            return
        }

        viewModelScope.launch {
            try {
                val request = SignUpRequest(name, email, password)
                val response = api.signUp(request)

                if (response.isSuccessful && response.body() != null) {
                    // If backend returns a token, save it
                    response.body()?.token?.let { token ->
                        tokenManager.saveToken(token)
                    }
                    onSuccess()
                } else {
                    onError("Signup failed: ${response.errorBody()?.string() ?: "Unknown error"}")
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("Server error: ${e.message}")
            }
        }
    }
    fun googleLogin(
        email: String?,
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = GoogleLoginRequest(email, idToken)
                val response = api.googleLogin(request)

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    tokenManager.saveToken(token)
                    onSuccess()
                } else {
                    onError("Google login failed")
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("Server error: ${e.message}")
            }
        }
    }
}
