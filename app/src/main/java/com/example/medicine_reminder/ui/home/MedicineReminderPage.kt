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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicine_reminder.uicomponents.HamburgerDropdownMenu



data class Medicine(
    val name: String,
    val strength: String,
    val type: String,
    val amount: String,
    val reminders: List<String>,
    val taken: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminderScreen(
    medicineList: List<Medicine>,
    onMoreClick: () -> Unit,
    onMenuOptionSelected: (String) -> Unit
) {
    val menuOptions = listOf("Add Medicine", "Setting", "Logout")
//    val medicines = remember {
//        listOf(
//            Medicine("Metformin 500mg tablets", "1 Pill", "8:00 AM", true),
//            Medicine("Paracetamol", "1 Pill", "12:00 PM", true),
//            Medicine("Omega - 4", "1 Pill", "3:00 PM", false),
//            Medicine("Vitamin C", "1 Pill", "8:30 PM", false)
//        )
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yash Guleria") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                actions = {
                    HamburgerDropdownMenu(
                        menuOptions = menuOptions,
                        onOptionSelected = { selected ->
                            onMenuOptionSelected(selected) // pass selection to Activity
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Check, contentDescription = "Progress") },
                    label = { Text("Progress") },
                    selected = false,
                    onClick = {

                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                    label = { Text("More") },
                    selected = false,
                    onClick = { onMoreClick() }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Today", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(medicineList) { medicine ->
                    MedicineCard(medicine)
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: Medicine) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Strength: ${medicine.strength}, " +
                        "Type: ${medicine.type}, " +
                        "Amount: ${medicine.amount}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(8.dp))

            Text("Reminders:", style = MaterialTheme.typography.bodyLarge)
            medicine.reminders.forEach { reminder ->
                Text("• $reminder", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun MedicineReminderScreenPreview() {
//    MedicineReminderScreen(
//        onMenuOptionSelected = { _->},
//        onMoreClick = {},
//    )
//}
