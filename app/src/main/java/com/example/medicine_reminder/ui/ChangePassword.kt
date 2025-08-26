package com.example.medicine_reminder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onChangePasswordClick: (String,String) -> Unit,
    onBackClick: () -> Unit
) {
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
//    var message by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .displayCutoutPadding()
            .background(MaterialTheme.colorScheme.primary),
        topBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TopRoundedBackButtonCircle {
                    (onBackClick)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Reset Password",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }


             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(30.dp),
                 horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically
             ){
                Text(
                    text = "Create a new password for your account. "+
                            "Password must be 8 character in length"
                )
            }


            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "New Password"
            )

            // New password
            TextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                visualTransformation = if (showNewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showNewPassword = !showNewPassword }) {
                        Icon(
                            imageVector = if (showNewPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showNewPassword) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Confirm Password"
            )

            // Confirm new password
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(
                            imageVector = if (showConfirmPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showConfirmPassword) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Change button
            Button(
                onClick = {
                    onChangePasswordClick(newPassword.text,confirmPassword.text)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Change Password")
            }

//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (message.isNotEmpty()) {
//                Text(text = message, color = MaterialTheme.colorScheme.primary)
//            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun ChangePasswordScreenPreview(){
    ChangePasswordScreen (
        onBackClick = { },
        onChangePasswordClick = {_,_->}
    )
}
