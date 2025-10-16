package com.example.medicine_reminder.navigation

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.medicine_reminder.R
import com.example.medicine_reminder.data.local.TokenManager
import com.example.medicine_reminder.ui.forgotpassword.ChangePasswordScreen
import com.example.medicine_reminder.ui.forgotpassword.ForgotPasswordScreen
import com.example.medicine_reminder.ui.home.AddMedicineScreen
import com.example.medicine_reminder.ui.home.MainScreen
import com.example.medicine_reminder.ui.home.MedicineReminderScreen
import com.example.medicine_reminder.ui.home.MoreScreen
import com.example.medicine_reminder.ui.home.ProgressScreen
import com.example.medicine_reminder.ui.login.LoginScreen
import com.example.medicine_reminder.ui.signup.RegisterScreen
import com.example.medicine_reminder.ui.otp.OtpVerificationPage
import com.example.medicine_reminder.uicomponents.HamburgerDropdownMenu
import com.example.medicine_reminder.viewmodel.LoginViewModel
import com.example.medicine_reminder.viewmodel.OtpVerificationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    activity: Activity
) {
    val tokenManager = androidx.hilt.navigation.compose.hiltViewModel<LoginViewModel>().tokenManager
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        /** LOGIN SCREEN */
        composable(route = Screen.Login.route) {
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
        composable(route = Screen.Signup.route) {
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
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
        ) { backStackEntry ->

            val viewModel: OtpVerificationViewModel = hiltViewModel()
            val email = backStackEntry.arguments?.getString("email") ?: ""

            OtpVerificationPage(
                email = email,
                onBackClick = {
                    navController.popBackStack()
                },
                onOtpVerified = {
                    // ✅ Use createRoute to pass email forward
                    navController.navigate(Screen.ChangePassword.createRoute(email)) {
                        popUpTo(Screen.OtpVerification.route) { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }


        /** CHANGE PASSWORD */
        composable(
            route = "${Screen.ChangePassword.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

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
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToOtp = { email ->
                    navController.navigate("${Screen.OtpVerification.route}/$email")
                }
            )
        }

        /** HOME SCREEN */
//        composable(route = Screen.Home.route) {
//            MedicineReminderScreen(
//                onMenuOptionSelected = { option ->
//                    when(option) {
//                        "Add Medicine" -> navController.navigate(Screen.AddMedicine.route)
//                        "Logout" -> {
//                            // Clear JWT / token if you have a login token manager
//                            tokenManager.clearToken()
//                            navController.navigate(Screen.Login.route) {
//                                popUpTo(Screen.Home.route) { inclusive = true }
//                            }
//                        }
//                        "Setting" -> {
//                            // Navigate to settings screen if you create one
////                            navController.navigate(Screen.Settings.route)
//                        }
//                    }
//                }
//            )
//
//        }

        /** ADD MEDICINE */
        composable(route = Screen.AddMedicine.route) {
            AddMedicineScreen(
                onBackClick = { navController.popBackStack() },
                makeScheduleClick = { name, strength, type, amount, start, finish, reminders ->
                    // Save medicine via ViewModel
                    navController.popBackStack()
                }
            )
        }

//        composable(route = Screen.Progress.route) {
//            ProgressScreen()
//        }
//
//        composable(route = Screen.More.route) {
//            MoreScreen()
//        }

        composable(route = Screen.Main.route) {
            MainScreen (
                onMenuOptionSelected = { option ->
                    when (option) {
                        "Add Medicine" -> navController.navigate(Screen.AddMedicine.route)
                        "Logout" -> {
                            // Clear JWT / token if you have a login token manager

                            tokenManager.clearToken()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }

                        "Setting" -> {
                            // Navigate to settings screen if you create one
//                            navController.navigate(Screen.Settings.route)
                        }
                    }
                }
            )
        }


    }
}

