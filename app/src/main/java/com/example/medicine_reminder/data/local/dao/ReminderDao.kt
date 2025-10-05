package com.example.medicine_reminder.data.local.dao

import androidx.room.*
import com.example.medicine_reminder.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: List<Reminder>): List<Long>

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    // 🔹 Live flow for UI updates
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId ORDER BY time ASC")
    fun getRemindersForMedicine(medicineId: Int): Flow<List<Reminder>>

    // 🔹 One-time fetch for internal logic
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId ORDER BY time ASC")
    suspend fun getRemindersForMedicineOnce(medicineId: Int): List<Reminder>

    @Query("SELECT * FROM reminders WHERE reminderId = :id LIMIT 1")
    suspend fun getReminderById(id: Int): Reminder?

    @Query("SELECT * FROM reminders ORDER BY time ASC")
    fun getAllRemindersFlow(): Flow<List<Reminder>> // 🔹 Flow for all reminders live

    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<Reminder>
}
