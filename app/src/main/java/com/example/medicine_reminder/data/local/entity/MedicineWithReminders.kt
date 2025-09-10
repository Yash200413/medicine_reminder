package com.example.medicine_reminder.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MedicineWithReminders(
    @Embedded val medicine: Medicine,
    @Relation(
        parentColumn = "medicineId",
        entityColumn = "medicineOwnerId"
    )
    val reminders: List<Reminder>
)
