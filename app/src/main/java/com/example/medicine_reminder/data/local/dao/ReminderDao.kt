package com.example.medicine_reminder.data.local.dao

import androidx.room.*
import com.example.medicine_reminder.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    // Single insert or replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder): Long

    // Batch insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: List<Reminder>): List<Long>

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    // Flow for UI: reminders of a medicine, live update
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId ORDER BY time ASC")
    fun getRemindersForMedicine(medicineId: Int): Flow<List<Reminder>>

    // One-time fetch for internal logic
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId ORDER BY time ASC")
    suspend fun getRemindersForMedicineOnce(medicineId: Int): List<Reminder>

    // Single reminder lookup
    @Query("SELECT * FROM reminders WHERE reminderId = :id LIMIT 1")
    suspend fun getReminderById(id: Int): Reminder?

    // Flow for all reminders (live updates)
    @Query("SELECT * FROM reminders ORDER BY time ASC")
    fun getAllRemindersFlow(): Flow<List<Reminder>>

    // One-time fetch for internal logic
    @Query("SELECT * FROM reminders")
    suspend fun getAllReminders(): List<Reminder>

    // Optional: lightweight projection for UI list
    @Query("SELECT reminderId, medicineOwnerId, time FROM reminders ORDER BY time ASC")
    fun getReminderListFlow(): Flow<List<ReminderLight>>
}

// Lightweight projection for faster UI rendering
data class ReminderLight(
    val reminderId: Int,
    val medicineOwnerId: Int,
    val time: Long
)
