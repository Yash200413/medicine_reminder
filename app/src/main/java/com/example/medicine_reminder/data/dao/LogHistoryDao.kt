package com.example.medicine_reminder.data.dao


import androidx.room.*
import com.example.medicine_reminder.data.entity.LogHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface LogHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogHistory)

    @Update
    suspend fun updateLog(log: LogHistory)

    @Delete
    suspend fun deleteLog(log: LogHistory)

    @Query("SELECT * FROM log_history WHERE reminderId = :reminderId ORDER BY takenTime DESC")
    fun getLogsForReminder(reminderId: Int): Flow<List<LogHistory>>
}
