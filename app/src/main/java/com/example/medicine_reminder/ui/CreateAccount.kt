package com.example.medicine_reminder.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicine_reminder.R
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onRegisterClick: (String,String,String,String) -> Unit,
    onSignWithGoogleClick: () -> Unit,
    onLogInClick: () -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .displayCutoutPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TopRoundedBackButtonCircle {
                    (onBackClick)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create An Account",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text(
                    text = "Lorem Ipsum is simply dummy text of text has been the industry's standard",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Text(text = "Name")
                // Name Field
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(text = "Email")
                // Email Field
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "New Password"
                )

                // Password
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Confirm Password"
                )

                // Confirm password
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
                        disabledIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Terms & Conditions
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                    Text(text = "Agree with ")
                    Text(
                        text = "Terms & Condition",
                        color = Color(0xFF4A90E2),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Register Button
            item {
                Button(
                    onClick = { onRegisterClick(name.text,email.text,password.text,confirmPassword.text) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A46E6) // purple button
                    )
                ) {
                    Text(text = "Register", color = Color.White, fontSize = 16.sp)
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item {
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
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Google Login
            item {
                OutlinedButton(
                    onClick = { onSignWithGoogleClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google), // Add google logo in drawable
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign in with Google", color = Color.Black)
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Already Have An Account? ")
                    Text(
                        text = "Log In",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable(onClick = onLogInClick)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onLogInClick = {},
        onBackClick = {},
        onRegisterClick = {_,_,_,_ ->},
        onSignWithGoogleClick = {}
    )
}
