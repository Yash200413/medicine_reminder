package com.example.medicine_reminder.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopRoundedBackButtonCircle(onBackClick: () -> Unit) {
    IconButton(
        onClick = onBackClick,
        modifier = Modifier
            .size(40.dp)
            .background(Color.LightGray, CircleShape)
    ) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = Color.White,
            contentDescription = "Back"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopRoundedBackButtonCirclePreview() {
    TopRoundedBackButtonCircle {  }
}
