package com.example.medicine_reminder.data.local

import com.example.medicine_reminder.data.local.dao.LogHistoryDao
import com.example.medicine_reminder.data.local.dao.MedicineDao
import com.example.medicine_reminder.data.local.dao.ReminderDao
import com.example.medicine_reminder.data.local.entity.LogHistory
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.MedicineWithReminders
import com.example.medicine_reminder.data.local.entity.Reminder
import kotlinx.coroutines.flow.Flow

class Repository(
    private val medicineDao: MedicineDao,
    private val reminderDao: ReminderDao,
    private val logHistoryDao: LogHistoryDao
) {
    // Medicines
    fun getAllMedicinesWithReminders(): Flow<List<MedicineWithReminders>> =
        medicineDao.getAllMedicinesWithReminders()

    suspend fun insertMedicine(medicine: Medicine): Int =
        medicineDao.insertMedicine(medicine).toInt()   // ✅ convert Long → Int

    suspend fun updateMedicine(medicine: Medicine) =
        medicineDao.updateMedicine(medicine)

    suspend fun deleteMedicine(medicine: Medicine) =
        medicineDao.deleteMedicine(medicine)


    // Reminders
    suspend fun insertReminder(reminder: Reminder): Int =
        reminderDao.insertReminder(reminder).toInt()   // ✅ convert Long → Int

    suspend fun insertReminders(reminders: List<Reminder>): List<Int> =
        reminderDao.insertReminders(reminders).map { it.toInt() }  // ✅ List<Long> → List<Int>

    suspend fun updateReminder(reminder: Reminder) =
        reminderDao.updateReminder(reminder)

    suspend fun deleteReminder(reminder: Reminder) =
        reminderDao.deleteReminder(reminder)

    // ❌ FIX: provide both Flow and suspend List for flexibility
    fun getRemindersForMedicineFlow(medicineId: Int): Flow<List<Reminder>> =
        reminderDao.getRemindersForMedicine(medicineId)

    suspend fun getRemindersForMedicine(medicineId: Int): List<Reminder> =
        reminderDao.getRemindersForMedicineOnce(medicineId)   // ✅ needs DAO function for one-time fetch

    suspend fun getMedicineById(id: Int): Medicine? = medicineDao.getMedicineById(id)

    suspend fun getReminderById(id: Int): Reminder? = reminderDao.getReminderById(id)

    // Logs
    fun getLogsForReminder(reminderId: Int) =
        logHistoryDao.getLogsForReminder(reminderId)

    suspend fun insertLog(log: LogHistory) =
        logHistoryDao.insertLog(log)

    suspend fun updateLog(log: LogHistory) =
        logHistoryDao.updateLog(log)

    suspend fun deleteLog(log: LogHistory) =
        logHistoryDao.deleteLog(log)
}
