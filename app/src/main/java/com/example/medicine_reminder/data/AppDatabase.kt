package com.example.medicine_reminder.data


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.medicine_reminder.data.dao.*
import com.example.medicine_reminder.data.entity.*

@Database(
    entities = [Medicine::class, Reminder::class, LogHistory::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
    abstract fun reminderDao(): ReminderDao
    abstract fun logHistoryDao(): LogHistoryDao
}
