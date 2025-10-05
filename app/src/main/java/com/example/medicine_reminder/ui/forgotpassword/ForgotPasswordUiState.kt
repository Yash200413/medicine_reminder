package com.example.medicine_reminder.ui.forgotpassword

data class ForgotPasswordUiState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)