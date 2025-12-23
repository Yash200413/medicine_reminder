package com.example.medicine_reminder.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicine_reminder.ui.forgotpassword.ChangePasswordScreen
import com.example.medicine_reminder.ui.forgotpassword.ForgotPasswordScreen
import com.example.medicine_reminder.ui.home.AddMedicineScreen
import com.example.medicine_reminder.ui.home.MainScreen
import com.example.medicine_reminder.ui.login.LoginScreen
import com.example.medicine_reminder.ui.otp.OtpVerificationPage
import com.example.medicine_reminder.ui.signup.RegisterScreen
import com.example.medicine_reminder.viewmodel.LoginViewModel
import com.example.medicine_reminder.viewmodel.OtpVerificationViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    activity: Activity
) {
    val tokenManager = hiltViewModel<LoginViewModel>().tokenManager

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        /** LOGIN SCREEN */
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Screen.Signup.route) },
                onForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }

        /** REGISTER SCREEN */
        composable(Screen.Signup.route) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },

                // FIXED: After registering → go to LOGIN screen
                onNavigateToHome = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },

                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }

        /** OTP SCREEN */
        composable(
            route = "${Screen.OtpVerification.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { entry ->
            val viewModel: OtpVerificationViewModel = hiltViewModel()
            val email = entry.arguments?.getString("email") ?: ""

            OtpVerificationPage(
                email = email,
                onBackClick = { navController.popBackStack() },
                onOtpVerified = {
                    navController.navigate(Screen.ChangePassword.createRoute(email)) {
                        popUpTo(Screen.OtpVerification.route) { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }

        /** CHANGE PASSWORD */
        composable(
            "${Screen.ChangePassword.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { entry ->
            val email = entry.arguments?.getString("email") ?: ""

            ChangePasswordScreen(
                email = email,
                onBackClick = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.ChangePassword.route) { inclusive = true }
                    }
                }
            )
        }

        /** FORGOT PASSWORD */
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToOtp = { email ->
                    navController.navigate("${Screen.OtpVerification.route}/$email")
                }
            )
        }

        /** ADD MEDICINE SCREEN */
        composable(Screen.AddMedicine.route) {
            AddMedicineScreen(
                onBackClick = { navController.popBackStack() },
                makeScheduleClick = { _, _, _, _, _, _, _ ->
                    navController.popBackStack()
                }
            )
        }

        /** MAIN SCREEN */
        composable(Screen.Main.route) {
            MainScreen(
                onLogOutClicked = {
                    tokenManager.clearToken()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                onMenuOptionSelected = { option ->
                    when (option) {
                        "Add Medicine" -> navController.navigate(Screen.AddMedicine.route)
                        "Logout" -> {
                            tokenManager.clearToken()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Main.route) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    }
}
