package com.example.medicine_reminder.data


import com.example.medicine_reminder.data.dao.*
import com.example.medicine_reminder.data.entity.*

class Repository(
    private val medicineDao: MedicineDao,
    private val reminderDao: ReminderDao,
    private val logHistoryDao: LogHistoryDao
) {
    // Medicine
    fun getAllMedicines() = medicineDao.getAllMedicines()
    suspend fun insertMedicine(medicine: Medicine) = medicineDao.insertMedicine(medicine)
    suspend fun updateMedicine(medicine: Medicine) = medicineDao.updateMedicine(medicine)
    suspend fun deleteMedicine(medicine: Medicine) = medicineDao.deleteMedicine(medicine)

    // Reminder
    fun getRemindersForMedicine(medicineId: Int) = reminderDao.getRemindersForMedicine(medicineId)
    suspend fun insertReminder(reminder: Reminder) = reminderDao.insertReminder(reminder)
    suspend fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)
    suspend fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)

    // Log
    fun getLogsForReminder(reminderId: Int) = logHistoryDao.getLogsForReminder(reminderId)
    suspend fun insertLog(log: LogHistory) = logHistoryDao.insertLog(log)
    suspend fun updateLog(log: LogHistory) = logHistoryDao.updateLog(log)
    suspend fun deleteLog(log: LogHistory) = logHistoryDao.deleteLog(log)
}
