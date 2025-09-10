package com.example.medicine_reminder.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = Medicine::class,
            parentColumns = ["medicineId"],
            childColumns = ["medicineOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("medicineOwnerId")]
)
data class Reminder(
    @PrimaryKey(autoGenerate = true) val reminderId: Int = 0,
    val time: String,
    val medicineOwnerId: Int
)
