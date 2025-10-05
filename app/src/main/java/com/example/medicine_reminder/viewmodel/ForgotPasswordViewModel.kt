package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.OtpRequest
import com.example.medicine_reminder.ui.forgotpassword.ForgotPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    fun sendOtp(email: String) {
        if (email.isBlank()) {
            _uiState.value = ForgotPasswordUiState(error = "Email cannot be empty")
            return
        }

        _uiState.value = ForgotPasswordUiState(loading = true)

        viewModelScope.launch {
            try {
                val request = OtpRequest(email)
                val response = api.sendOtp(request)

                if (response.isSuccessful && response.body()?.message == true) {
                    _uiState.value = ForgotPasswordUiState(success = true)
                } else {
                    _uiState.value = ForgotPasswordUiState(error = "Invalid email or failed to send OTP")
                }
            } catch (e: IOException) {
                _uiState.value = ForgotPasswordUiState(error = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = ForgotPasswordUiState(error = "Server error: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = ForgotPasswordUiState()
    }
}

