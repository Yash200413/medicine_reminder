package com.example.medicine_reminder.ui

import com.example.medicine_reminder.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onLoginClick: (String, String) -> Unit,
    onSignInWithGoogleClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .displayCutoutPadding()
            .background(MaterialTheme.colorScheme.primary),
        topBar = {
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Login Now",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "Email"
            )

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Password"
            )

            // Password Field
            TextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPassword) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Forgot Password?",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable(onClick = onForgotPasswordClick)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Button
            Button(
                onClick = {onLoginClick(email,password)},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Login", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider with text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
                Text("  OR  ")
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Sign In Button (UI only)
            Button(
                onClick = {onSignInWithGoogleClick()},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Replace with your google logo (png in res/drawable)
                Image(
                    painter = painterResource(id = R.drawable.ic_google), // Add google icon in drawable
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen (
        onForgotPasswordClick = {},
        onLoginClick = { _, _ -> },
        onSignInWithGoogleClick = {}
    )
}
