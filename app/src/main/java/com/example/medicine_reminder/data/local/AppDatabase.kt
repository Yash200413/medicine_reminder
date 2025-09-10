package com.example.medicine_reminder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.medicine_reminder.data.local.dao.LogHistoryDao
import com.example.medicine_reminder.data.local.dao.MedicineDao
import com.example.medicine_reminder.data.local.dao.ReminderDao
import com.example.medicine_reminder.data.local.entity.LogHistory
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder

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