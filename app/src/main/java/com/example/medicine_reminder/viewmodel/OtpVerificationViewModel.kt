package com.example.medicine_reminder.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.VerifyOtp
import com.example.medicine_reminder.model.VerifyOtpResponse
import com.example.medicine_reminder.ui.otp.OtpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

// --------------------- UI State --------------------- //

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _uiState.value = OtpUiState(isLoading = true)

            try {
                Log.d("log","email is $email and otp is $otp")
                val response = api.verifyOtp(VerifyOtp(email, otp))
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    if (result.message) {
                        _uiState.value = OtpUiState(successResponse = true)
                    } else {
                        _uiState.value = OtpUiState(errorMessage = "Invalid OTP")
                    }
                } else {
                    _uiState.value = OtpUiState(errorMessage = "Server error: ${response.code()}")
                }
            } catch (e: IOException) {
                _uiState.value = OtpUiState(errorMessage = "Network error: ${e.message}")
            } catch (e: HttpException) {
                _uiState.value = OtpUiState(errorMessage = "Server error: ${e.message}")
            } catch (e: Exception) {
                _uiState.value = OtpUiState(errorMessage = e.message ?: "Unexpected error")
            }
        }
    }

    fun clearState() {
        _uiState.value = OtpUiState()
    }
}
