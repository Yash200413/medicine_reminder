package com.example.medicine_reminder.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopRoundedBackButtonCircle(onBackClick: () -> Unit) {
    Button(
        onClick = onBackClick,
        shape = CircleShape,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopRoundedBackButtonCirclePreview() {
    TopRoundedBackButtonCircle {  }
}
