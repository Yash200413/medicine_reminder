package com.example.medicine_reminder.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicine_reminder.uicomponents.TopRoundedBackButtonCircle
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    makeScheduleClick: (String, String, String, String, String, String, String, String, List<String>) -> Unit,
    onBackClick: () -> Unit
) {
    data class FrequencyOption(
        val label: String,   // What user sees
        val hours: Int?      // Interval in hours (null = special cases like before meals)
    )

    var medicineName by remember { mutableStateOf(TextFieldValue("")) }
    var strength by remember { mutableStateOf(TextFieldValue("")) }
    var whenToTake by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var start by remember { mutableStateOf("") }
    var finish by remember { mutableStateOf("") }
    val selectedDays = remember { mutableStateListOf<String>() }

    val frequencyOptions = listOf(
        FrequencyOption("Once a day", 24),
        FrequencyOption("Twice a day", 12),
        FrequencyOption("Thrice a day", 8),
        FrequencyOption("Every 6 hours", 6),
        FrequencyOption("Every 8 hours", 8),
        FrequencyOption("Every 12 hours", 12),
        FrequencyOption("As needed", null)
    )
    val amountOptions: List<String> = listOf(
        "½",       // half
        "1",
        "1½",
        "2",
        "3",
        "5 ml",
        "10 ml",
        "15 ml"
    )
    val typeOptions: List<String> = listOf(
        "Tablet",
        "Capsule",
        "Syrup",
        "Injection",
        "Drop",
        "Ointment",
        "Inhaler"
    )
    val whenToTakeOptions = listOf(
        "Before breakfast",
        "After breakfast",
        "Before lunch",
        "After lunch",
        "Before dinner",
        "After dinner",
        "At bedtime",
        "Morning",
        "Afternoon",
        "Evening",
        "Night",
        "Empty stomach",
        "With food",
        "With water",
        "As directed by doctor"
    )
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                TopRoundedBackButtonCircle {
                    (onBackClick)
                }
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
                Text("When To Take")
                DropdownMenuBox(
                    placeholder = "When To Take",
                    options = whenToTakeOptions,
                    value = whenToTake,
                    onValueChange = { whenToTake = it }
                )
            }

            item {
                Text("Type")
                DropdownMenuBox(
                    placeholder = "Type",
                    options = typeOptions,
                    value = type,
                    onValueChange = { type = it })
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Frequency")
                        Spacer(modifier = Modifier.height(4.dp))
                        DropdownMenuBox(
                            placeholder = "Frequency",
                            options = frequencyOptions.map { it.label },
                            value = frequency,
                            onValueChange = { frequency = it }
                        )
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
                // Days of Week Row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                        item {
                            val isSelected = selectedDays.contains(day)
                            Spacer(modifier = Modifier.width(8.dp))
                            AssistChip(
                                onClick = {
                                    if (isSelected) {
                                        selectedDays.remove(day)
                                    } else {
                                        selectedDays.add(day)
                                    }
                                },
                                border = null,
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant,
                                    labelColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(12.dp),
                                label = {
                                    Text(day)
                                }
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        makeScheduleClick(
                            medicineName.text,
                            strength.text,
                            whenToTake,
                            type,
                            amount,
                            frequency,
                            start,
                            finish,
                            selectedDays
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
                ) {
                    Text("Make Schedule", color = Color.White)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
    placeholder: String,
    modifier: Modifier = Modifier,
    value: String,                     // ⬅️ current selected value
    onValueChange: (String) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            placeholder = { Text(placeholder) },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedText = selectionOption
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    value: String,                     // ⬅️ current selected value
    onValueChange: (String) -> Unit,
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    // DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    TextField(
        value = selectedDate,
        onValueChange = { },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Select Date") },
        readOnly = true, // ✅ stops keyboard but allows clicks
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select date",
                modifier = Modifier.clickable { datePickerDialog.show() } // 👈 opens calendar
            )
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun AddMedicineScreenPreview() {
    AddMedicineScreen(
        onBackClick = {},
        makeScheduleClick = {_,_,_,_,_,_,_,_,_->}
    )
}