package com.example.medicine_reminder.data.local

import com.example.medicine_reminder.data.local.dao.LogHistoryDao
import com.example.medicine_reminder.data.local.dao.MedicineDao
import com.example.medicine_reminder.data.local.dao.ReminderDao
import com.example.medicine_reminder.data.local.entity.LogHistory
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

class Repository(
    private val medicineDao: MedicineDao,
    private val reminderDao: ReminderDao,
    private val logHistoryDao: LogHistoryDao
) {

    // -------------------- Medicines -------------------- //

    suspend fun insertMedicine(medicine: Medicine): Int =
        medicineDao.insertMedicine(medicine).toInt()

    suspend fun updateMedicine(medicine: Medicine) =
        medicineDao.updateMedicine(medicine)

    suspend fun deleteMedicine(medicine: Medicine) =
        medicineDao.deleteMedicine(medicine)

    suspend fun getMedicineById(medicineId: Int): Medicine? =
        medicineDao.getMedicineById(medicineId)

    // Optional: Live Flow for observing all medicines
    @Deprecated("Use MedicinesWithReminders in ViewModel for combined flow")
    fun getAllMedicinesFlow(): Flow<List<Medicine>> = medicineDao.getAllMedicinesFlow()

    // -------------------- Reminders -------------------- //

    suspend fun insertReminder(reminder: Reminder): Int =
        reminderDao.insertReminder(reminder).toInt()

    suspend fun updateReminder(reminder: Reminder) =
        reminderDao.updateReminder(reminder)

    suspend fun deleteReminder(reminder: Reminder) =
        reminderDao.deleteReminder(reminder)

    // 🔹 One-time fetch (for internal logic like cancel/reschedule)
    suspend fun getRemindersForMedicine(medicineId: Int): List<Reminder> =
        reminderDao.getRemindersForMedicineOnce(medicineId)

    suspend fun getAllReminders(): List<Reminder> =
        reminderDao.getAllReminders()

    suspend fun getReminderById(reminderId: Int): Reminder? =
        reminderDao.getReminderById(reminderId)

    // 🔹 Live Flow for UI updates
    fun getRemindersForMedicineFlow(medicineId: Int): Flow<List<Reminder>> =
        reminderDao.getRemindersForMedicine(medicineId)

    fun getAllRemindersFlow(): Flow<List<Reminder>> =
        reminderDao.getAllRemindersFlow()

    // -------------------- Logs -------------------- //

    fun getLogsForReminder(reminderId: Int) =
        logHistoryDao.getLogsForReminder(reminderId)

    suspend fun insertLog(log: LogHistory) =
        logHistoryDao.insertLog(log)

    suspend fun updateLog(log: LogHistory) =
        logHistoryDao.updateLog(log)

    suspend fun deleteLog(log: LogHistory) =
        logHistoryDao.deleteLog(log)
}
