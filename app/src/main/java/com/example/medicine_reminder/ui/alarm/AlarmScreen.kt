package com.example.medicine_reminder.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medicine_reminder.viewmodel.AlarmViewModel

@Composable
fun AlarmScreen(
    medicineName: String,
    onSnooze: () -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Medicine Reminder", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text("It’s time to take $medicineName", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSnooze,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Snooze 10 min")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Dismiss")
            }
        }
    }
}



@Preview(showSystemUi = true)
@Composable
fun AlarmScreenPreview() {
    AlarmScreen(
        medicineName = "ddd",
        onDismiss = {},
        onSnooze = {}
    )
}

