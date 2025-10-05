package com.example.medicine_reminder.ui.otp

import com.example.medicine_reminder.model.VerifyOtpResponse

data class OtpUiState(
    val isLoading: Boolean = false,
    val successResponse: Boolean = false,
    val errorMessage: String? = null
)
