package com.example.medicine_reminder.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicine_reminder.viewmodel.MedicineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminderScreen(
    modifier: Modifier,
    viewModel: MedicineViewModel = hiltViewModel() // <-- Hilt injected ViewModel
) {
    val medicineList by viewModel.medicines.collectAsState()

//    val menuOptions = listOf("Add Medicine", "Setting", "Logout")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
    ) {
        Text("Today", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(medicineList) { item ->
                MedicineCard(
                    name = item.medicine.name,
                    strength = item.medicine.strength,
                    type = item.medicine.type,
                    amount = item.medicine.amount,
                    reminders = item.reminders.map { it.time }
                )
            }
        }
    }
}


@Composable
fun MedicineCard(
    name: String,
    strength: String,
    type: String,
    amount: String,
    reminders: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Strength: $strength, Type: $type, Amount: $amount",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(8.dp))

            Text("Reminders:", style = MaterialTheme.typography.bodyLarge)
            reminders.forEach { reminder ->
                Text("• $reminder", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MedicineReminderScreenPreview() {
    // Dummy preview UI
    MedicineReminderScreen(
        modifier = Modifier
//        onNavigateTo = {},
//        onMoreClick = {},
//        onMenuOptionSelected = {}
    )
}
