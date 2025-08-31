package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.OtpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    fun sendOtp(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank()) {
            onError("Email cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                val request = OtpRequest(email)
                val response = api.sendOtp(request)

                if (response.isSuccessful && response.body()?.message == true) {
                    onSuccess()
                } else {
                    onError("Invalid email or failed to send OTP")
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("Server error: ${e.message}")
            }
        }
    }
}
