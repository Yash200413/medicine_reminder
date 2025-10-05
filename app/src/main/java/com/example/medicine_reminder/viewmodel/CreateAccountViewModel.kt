package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.SignUpRequest
import com.example.medicine_reminder.ui.signup.CreateAccountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState: StateFlow<CreateAccountUiState> = _uiState

    /** Reset state after navigation or showing error */
    fun resetState() {
        _uiState.value = CreateAccountUiState()
    }

    /** Sign up with email/password */
    fun signUp(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _uiState.value = CreateAccountUiState(error = "All fields are required")
            return
        }

        if (password != confirmPassword) {
            _uiState.value = CreateAccountUiState(error = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = CreateAccountUiState(loading = true)
            try {
                val request = SignUpRequest(name, email, password)
                val response = api.signUp(request)
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.token?.let { token ->
                        tokenManager.saveToken(token)
                    }
                    _uiState.value = CreateAccountUiState(success = true)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    _uiState.value = CreateAccountUiState(error = "Signup failed: $errorMsg")
                }
            } catch (e: IOException) {
                _uiState.value = CreateAccountUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = CreateAccountUiState(error = "Server error: ${e.message}")
            }
        }
    }

    /** Google login */
    fun googleLogin(email: String?, idToken: String) {
        viewModelScope.launch {
            _uiState.value = CreateAccountUiState(loading = true)
            try {
                val request = GoogleLoginRequest(email, idToken)
                val response = api.googleLogin(request)
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    tokenManager.saveToken(token)
                    _uiState.value = CreateAccountUiState(success = true)
                } else {
                    _uiState.value = CreateAccountUiState(error = "Google login failed")
                }
            } catch (e: IOException) {
                _uiState.value = CreateAccountUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = CreateAccountUiState(error = "Server error: ${e.message}")
            }
        }
    }
}
