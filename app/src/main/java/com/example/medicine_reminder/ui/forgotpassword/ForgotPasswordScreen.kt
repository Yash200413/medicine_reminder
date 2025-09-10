package com.example.medicine_reminder.ui.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onResetClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

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
                    onBackClick()
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Forgot Password",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Enter your email to reset your password. A verification code will be sent to this email.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Email",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onResetClick(email.text) },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Code")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(
        onResetClick = { },
        onBackClick = { }
    )
}
