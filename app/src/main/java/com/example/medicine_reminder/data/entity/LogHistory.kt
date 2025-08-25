package com.example.medicine_reminder.data.entity


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "log_history",
    foreignKeys = [
        ForeignKey(
            entity = Reminder::class,
            parentColumns = ["reminderId"],
            childColumns = ["reminderId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LogHistory(
    @PrimaryKey(autoGenerate = true) val logId: Int = 0,
    val reminderId: Int,
    val takenTime: Long,
    val status: String // "Taken", "Missed", "Snoozed"
)
