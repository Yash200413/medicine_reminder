package com.example.medicine_reminder.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val medicineId: Int = 0,
    val name: String,
    val strength: String,
    val dosage: String,
//    val instructions: String,
    val type: String,
    val startDate: Long,
    val endDate: Long,
    val pattern: String,
    val time: Long?
)
