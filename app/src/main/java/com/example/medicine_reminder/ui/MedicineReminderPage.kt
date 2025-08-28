package com.example.medicine_reminder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Medicine(
    val name: String,
    val dose: String,
    val time: String,
    val taken: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminderScreen() {
    val medicines = remember {
        listOf(
            Medicine("Metformin 500mg tablets", "1 Pill", "8:00 AM", true),
            Medicine("Paracetamol", "1 Pill", "12:00 PM", true),
            Medicine("Omega - 4", "1 Pill", "3:00 PM", false),
            Medicine("Vitamin C", "1 Pill", "8:30 PM", false)
        )
    }

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
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
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
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                    label = { Text("More") },
                    selected = false,
                    onClick = {}
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
                items(medicines) { medicine ->
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(medicine.name, fontWeight = FontWeight.Bold)
                Text(medicine.dose, style = MaterialTheme.typography.bodyMedium)
                Text(medicine.time, style = MaterialTheme.typography.bodySmall)
            }

            if (medicine.taken) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Taken",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .padding(6.dp)
                )
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun MedicineReminderScreenPreview(){
    MedicineReminderScreen()
}
