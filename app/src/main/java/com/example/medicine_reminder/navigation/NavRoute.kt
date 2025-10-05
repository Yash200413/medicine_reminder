package com.example.medicine_reminder.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")
    object Signup : Screen("signup")

    object OtpVerification : Screen("otp_verification") {
        fun createRoute(email: String) = "otp_verification/$email"
    }
    object ChangePassword : Screen("change_password") {
        fun createRoute(email: String) = "change_password/$email"
    }

    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object AddMedicine : Screen("add_medicine")
    object ReminderList : Screen("reminder_list")
}
