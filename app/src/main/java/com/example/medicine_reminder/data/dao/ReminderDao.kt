package com.example.medicine_reminder.data.dao


import androidx.room.*
import com.example.medicine_reminder.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminder WHERE medicineId = :medicineId")
    fun getRemindersForMedicine(medicineId: Int): Flow<List<Reminder>>
}
