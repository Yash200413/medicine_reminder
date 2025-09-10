package com.example.medicine_reminder.data.local.dao


import androidx.room.*
import com.example.medicine_reminder.data.local.entity.MedicineWithReminders
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

    // 🔹 For observing in UI
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId")
    fun getRemindersForMedicine(medicineId: Int): Flow<List<Reminder>>

    // 🔹 For one-time fetch (delete/update logic)
    @Query("SELECT * FROM reminders WHERE medicineOwnerId = :medicineId")
    suspend fun getRemindersForMedicineOnce(medicineId: Int): List<Reminder>

    @Query("SELECT * FROM reminders WHERE reminderId = :id LIMIT 1")
    suspend fun getReminderById(id: Int): Reminder?
}

