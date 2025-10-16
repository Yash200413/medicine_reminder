package com.example.medicine_reminder.data.local.dao

import androidx.room.*
import com.example.medicine_reminder.data.local.entity.Medicine
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    // Insert single or multiple medicines efficiently
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicines(medicines: List<Medicine>): List<Long>

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    // Fast lookup by indexed ID
    @Query("SELECT * FROM medicines WHERE medicineId = :id LIMIT 1")
    suspend fun getMedicineById(id: Int): Medicine?

    // Use Flow to observe all medicines efficiently, sorted by name
    @Query("SELECT * FROM medicines ORDER BY name ASC")
    fun getAllMedicinesFlow(): Flow<List<Medicine>>

    // Optional: select only required columns for lightweight queries
    @Query("SELECT medicineId, name FROM medicines ORDER BY name ASC")
    fun getMedicineNamesFlow(): Flow<List<MedicineNameProjection>>
}

// Projection for lightweight queries
data class MedicineNameProjection(
    val medicineId: Int,
    val name: String
)
