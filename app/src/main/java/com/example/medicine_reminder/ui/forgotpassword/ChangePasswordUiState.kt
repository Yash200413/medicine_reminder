package com.example.medicine_reminder.ui.forgotpassword

data class ChangePasswordUiState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)