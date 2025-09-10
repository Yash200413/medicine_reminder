package com.example.medicine_reminder.data.local.dao

import androidx.room.*
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.MedicineWithReminders
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicine(medicine: Medicine): Long

    @Update
    suspend fun updateMedicine(medicine: Medicine)

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)

    @Transaction
    @Query("SELECT * FROM medicines")
    fun getAllMedicinesWithReminders(): Flow<List<MedicineWithReminders>>

    @Query("SELECT * FROM medicines WHERE medicineId = :id LIMIT 1")
    suspend fun getMedicineById(id: Int): Medicine?
}

