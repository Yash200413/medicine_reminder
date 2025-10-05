package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.ui.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ApiService,
    val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    /** Reset state after navigation or showing error */
    fun resetState() {
        _uiState.value = LoginUiState()
    }

    /** Login with email/password */
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState(error = "Please enter both email and password")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(loading = true)
            try {
                val request = LoginRequest(email, password)
                val response = api.login(request)

                val token = response.body()?.token
                if (response.isSuccessful && !token.isNullOrEmpty()) {
                    tokenManager.saveToken(token)
                    _uiState.value = LoginUiState(success = true)
                } else {
                    val errorMsg = response.body()?.token ?: response.errorBody()?.string() ?: "Invalid credentials"
                    _uiState.value = LoginUiState(error = errorMsg)
                }
            } catch (e: IOException) {
                _uiState.value = LoginUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = LoginUiState(error = "Server error: ${e.message}")
            }
        }
    }

    /** Google login */
    fun googleLogin(email: String?, idToken: String) {
        if (email.isNullOrEmpty() || idToken.isEmpty()) {
            _uiState.value = LoginUiState(error = "Google login failed: invalid data")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(loading = true)
            try {
                val request = GoogleLoginRequest(email, idToken)
                val response = api.googleLogin(request)

                val token = response.body()?.token
                if (response.isSuccessful && !token.isNullOrEmpty()) {
                    tokenManager.saveToken(token)
                    _uiState.value = LoginUiState(success = true)
                } else {
                    val errorMsg = response.body()?.token ?: response.errorBody()?.string() ?: "Google login failed"
                    _uiState.value = LoginUiState(error = errorMsg)
                }
            } catch (e: IOException) {
                _uiState.value = LoginUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = LoginUiState(error = "Server error: ${e.message}")
            }
        }
    }

    /** Validate existing JWT token */
    fun checkForJwtAndLogin() {
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            _uiState.value = LoginUiState(error = "No token found")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState(loading = true)
            try {
                val response = api.validateToken("Bearer $token")
                if (response.isSuccessful) {
                    _uiState.value = LoginUiState(success = true)
                } else {
                    tokenManager.clearToken()
                    _uiState.value = LoginUiState(error = "Token invalid")
                }
            } catch (e: IOException) {
                _uiState.value = LoginUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = LoginUiState(error = "Server error: ${e.message}")
            }
        }
    }
}
