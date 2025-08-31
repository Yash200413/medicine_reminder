package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Please enter both email and password")
            return
        }

        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password)
                val response = api.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    tokenManager.saveToken(token)
                    onSuccess()
                } else {
                    onError("Invalid credentials")
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

    fun checkForJwtAndLogin(
        onValid: () -> Unit,
        onInvalid: () -> Unit,
        onError: (String) -> Unit
    ) {
        val token = tokenManager.getToken()

        if (token.isNullOrEmpty()) {
            onInvalid()
            return
        }

        viewModelScope.launch {
            try {
                val response = api.validateToken("Bearer $token")

                if (response.isSuccessful) {
                    onValid()
                } else {
                    tokenManager.clearToken()
                    onInvalid()
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("Server error: ${e.message}")
            }
        }
    }
}
