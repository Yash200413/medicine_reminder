package com.example.medicine_reminder.data.local.dao

import androidx.room.*
import com.example.medicine_reminder.data.local.entity.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine): Long

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Query("SELECT * FROM medicines WHERE medicineId = :id")
    suspend fun getMedicineById(id: Int): Medicine?

    // ✅ Flow to observe all medicines for live UI updates
    @Query("SELECT * FROM medicines ORDER BY name ASC")
    fun getAllMedicinesFlow(): Flow<List<Medicine>>
}
