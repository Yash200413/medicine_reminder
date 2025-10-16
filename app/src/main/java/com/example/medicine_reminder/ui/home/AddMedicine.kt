package com.example.medicine_reminder.ui.home

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.uicomponents.DatePickerTextField
import com.example.medicine_reminder.uicomponents.DropdownMenuBox
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle
import com.example.medicine_reminder.viewmodel.MedicineViewModel
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    viewModel: MedicineViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    makeScheduleClick: (
        String,  // name
        String,  // strength
        String,  // type
        String,  // amount
        Long,    // startDate
        Long,    // finishDate
        List<String> //reminders
    ) -> Unit
) {
    val context = LocalContext.current

    var reminders by remember { mutableStateOf(listOf<String>()) }
    var medicineName by remember { mutableStateOf(TextFieldValue("")) }
    var strength by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var start by remember { mutableStateOf(0L) }
    var finish by remember { mutableStateOf(0L) }

    val amountOptions = listOf("½", "1", "1½", "2", "3", "5 ml", "10 ml", "15 ml")
    val typeOptions =
        listOf("Tablet", "Capsule", "Syrup", "Injection", "Drop", "Ointment", "Inhaler")

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .displayCutoutPadding(),
                horizontalArrangement = Arrangement.Start
            ) {
                TopRoundedBackButtonCircle { onBackClick() }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Add Medicine",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                // Photo Section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEFEFEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Medication,
                            contentDescription = "Add Photo",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Add photo", color = Color.Gray)
                }
            }

            item {
                Text("Medicine Name")
                TextField(
                    value = medicineName,
                    onValueChange = { medicineName = it },
                    placeholder = { Text("Medicine Name") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Text("Strength")
                TextField(
                    value = strength,
                    onValueChange = { strength = it },
                    placeholder = { Text("Strength") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }



            item {
                Text("Type")
                DropdownMenuBox(
                    placeholder = "Type",
                    options = typeOptions,
                    value = type,
                    onValueChange = { type = it }
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Amount")
                        Spacer(modifier = Modifier.height(4.dp))
                        DropdownMenuBox(
                            placeholder = "Amount",
                            options = amountOptions,
                            value = amount,
                            onValueChange = { amount = it }
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        val cal = Calendar.getInstance()
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                val time =
                                    String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
                                reminders = reminders + time
                            },
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Reminder")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    reminders.forEach { time ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = time, style = MaterialTheme.typography.bodyLarge)
                                IconButton(onClick = { reminders = reminders - time }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete Reminder"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Start")
                        Spacer(modifier = Modifier.height(4.dp))
                        DatePickerTextField(
                            value = start,
                            onValueChange = { start = it }
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Finish")
                        Spacer(modifier = Modifier.height(4.dp))
                        DatePickerTextField(
                            value = finish,
                            onValueChange = { finish = it }
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        val medicine = Medicine(
                            name = medicineName.text,
                            strength = strength.text,
                            type = type,
                            amount = amount,
                            startDate = start,
                            finishDate = finish
                        )
                        // Add medicine + reminders using ViewModel
                        viewModel.addMedicine(medicine, reminders)

                        // Optionally notify parent
                        makeScheduleClick(
                            medicineName.text,
                            strength.text,
                            type,
                            amount,
                            start,
                            finish,
                            reminders
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Make Schedule", color = Color.White)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AddMedicineScreenPreview() {
    AddMedicineScreen(
        onBackClick = {},
        makeScheduleClick = { _, _, _, _, _, _, _ -> }
    )
}
