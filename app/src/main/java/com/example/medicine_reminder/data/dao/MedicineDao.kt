package com.example.medicine_reminder.data.dao

import androidx.room.*
import com.example.medicine_reminder.data.entity.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine)

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Query("SELECT * FROM medicine ORDER BY name ASC")
    fun getAllMedicines(): Flow<List<Medicine>>
}
