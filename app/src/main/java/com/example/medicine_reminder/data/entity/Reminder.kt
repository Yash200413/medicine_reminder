package com.example.medicine_reminder.data.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminder",
    foreignKeys = [
        ForeignKey(
            entity = Medicine::class,
            parentColumns = ["medicineId"],
            childColumns = ["medicineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderId: Int = 0,
    val medicineId: Int,
    val time: Long,           // Store as millis
    val repeatPattern: String,
    val isActive: Boolean = true
)
