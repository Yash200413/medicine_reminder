package com.example.medicine_reminder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// MedicineEntity.kt
@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val medicineId: Int = 0,
    val name: String,
    val strength: String,
    val type: String,
    val amount: String,
    val startDate: Long,
    val finishDate: Long
)
