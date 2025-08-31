package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.VerifyOtp
import com.example.medicine_reminder.model.VerifyOtpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel() {

    fun verifyOtp(
        email: String,
        otp: String,
        onSuccess: (VerifyOtpResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = api.verifyOtp(VerifyOtp(email, otp))

                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    if (result.message) {
                        onSuccess(result)
                    } else {
                        onError("Invalid OTP")
                    }
                } else {
                    onError("Server error: ${response.code()}")
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("Server error: ${e.message}")
            } catch (e: Exception) {
                onError(e.message ?: "Unexpected error")
            }
        }
    }
}
