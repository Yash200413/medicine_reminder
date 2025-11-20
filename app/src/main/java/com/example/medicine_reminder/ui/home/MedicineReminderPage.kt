package com.example.medicine_reminder.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder
import com.example.medicine_reminder.viewmodel.MedicineViewModel
import com.example.medicine_reminder.viewmodel.MedicineViewModel.MedicineWithReminders

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminderScreen(
    modifier: Modifier = Modifier,
    onNavigateToAddMedicine: () -> Unit,
    onNavigateToEditMedicine: (MedicineWithReminders) -> Unit,
    viewModel: MedicineViewModel = hiltViewModel()
) {
    val medicineList by viewModel.medicines.collectAsState()

    // State for Bottom Sheet
    var selectedMedicine by remember { mutableStateOf<MedicineWithReminders?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text("Today", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            if (medicineList.isEmpty()) {
                // Empty state
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No medicines yet. Tap + to add", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(medicineList) { item ->
                        MedicineCard(
                            medicineWithReminders = item,
                            onCardClick = {
                                selectedMedicine = item
                                showBottomSheet = true
                            }
                        )
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onNavigateToAddMedicine,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Medicine")
        }

        // Show Bottom Sheet
        if (showBottomSheet && selectedMedicine != null) {
            MedicineOptionsBottomSheet(
                medicineName = selectedMedicine!!.medicine.name,
                onEdit = {
                    onNavigateToEditMedicine(selectedMedicine!!)
                    showBottomSheet = false
                },
                onDelete = {
                    viewModel.deleteMedicine(selectedMedicine!!.medicine)
                    showBottomSheet = false
                },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun MedicineCard(
    medicineWithReminders: MedicineWithReminders,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = medicineWithReminders.medicine.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Strength: ${medicineWithReminders.medicine.strength} | " +
                        "Type: ${medicineWithReminders.medicine.type} | " +
                        "Amount: ${medicineWithReminders.medicine.amount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(8.dp))

            if (medicineWithReminders.reminders.isNotEmpty()) {
                Text(
                    "Reminders:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                medicineWithReminders.reminders.forEach { reminder ->
                    Text("• ${reminder.time}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MedicineReminderScreenPreview() {
    val dummyReminders = listOf("08:00 AM", "02:00 PM")
    val dummyList = listOf(
        MedicineWithReminders(
            medicine = Medicine(
                medicineId = 1,
                name = "Paracetamol",
                strength = "500mg",
                type = "Tablet",
                amount = "2",
                startDate = 1700000000000L, // Nov 14, 2023
                finishDate = 1700086400000L // Nov 15, 2023

            ),
            reminders = dummyReminders.map { Reminder(time = it, medicineOwnerId = 1) }
        ),
        MedicineWithReminders(
            medicine = Medicine(
                medicineId = 2,
                name = "Vitamin D",
                strength = "1000 IU",
                type = "Capsule",
                amount = "1",
                startDate = 1700000000000L, // Nov 14, 2023
                finishDate = 1700086400000L // Nov 15, 2023
            ),
            reminders = dummyReminders.map { Reminder(time = it, medicineOwnerId = 2) }
        )
    )

    // Use a temporary StateFlow for preview
    var selectedMedicine by remember { mutableStateOf<MedicineWithReminders?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(dummyList) { item ->
                MedicineCard(
                    medicineWithReminders = item,
                    onCardClick = {
                        selectedMedicine = item
                        showBottomSheet = true
                    }
                )
            }
        }

        if (showBottomSheet && selectedMedicine != null) {
            MedicineOptionsBottomSheet(
                medicineName = selectedMedicine!!.medicine.name,
                onEdit = { /* preview */ },
                onDelete = { /* preview */ },
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}
