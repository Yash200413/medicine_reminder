package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.local.Repository
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.MedicineWithReminders
import com.example.medicine_reminder.data.local.entity.Reminder
import com.example.medicine_reminder.reminder.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: Repository,
    private val scheduler: ReminderScheduler   // ✅ injected scheduler
) : ViewModel() {

    val medicines: StateFlow<List<MedicineWithReminders>> =
        repository.getAllMedicinesWithReminders()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addMedicine(medicine: Medicine, reminders: List<String>) {
        viewModelScope.launch {
            // ✅ Repository insert returns Int (ensure DAO insert returns Int)
            val medicineId: Int = repository.insertMedicine(medicine)

            reminders.forEach { time ->
                val reminder = Reminder(time = time, medicineOwnerId = medicineId)
                val reminderId: Int = repository.insertReminder(reminder)

                // ✅ Schedule alarm after saving
                scheduler.scheduleReminder(
                    medicine.copy(medicineId = medicineId),
                    reminder.copy(reminderId = reminderId)
                )
            }
        }
    }

    fun updateMedicine(medicine: Medicine) = viewModelScope.launch {
        repository.updateMedicine(medicine)
    }

    fun deleteMedicine(medicine: Medicine) = viewModelScope.launch {
        // ✅ cancel all reminders before deleting
        val reminders = repository.getRemindersForMedicine(medicine.medicineId)
        reminders.forEach { scheduler.cancelReminder(it.reminderId) }
        repository.deleteMedicine(medicine)
    }

    fun addReminder(reminder: Reminder, medicine: Medicine) = viewModelScope.launch {
        val reminderId: Int = repository.insertReminder(reminder)
        scheduler.scheduleReminder(medicine, reminder.copy(reminderId = reminderId))
    }

    fun updateReminder(reminder: Reminder, medicine: Medicine) = viewModelScope.launch {
        scheduler.cancelReminder(reminder.reminderId) // cancel old
        repository.updateReminder(reminder)
        scheduler.scheduleReminder(medicine, reminder)
    }

    fun deleteReminder(reminder: Reminder) = viewModelScope.launch {
        scheduler.cancelReminder(reminder.reminderId)
        repository.deleteReminder(reminder)
    }
}
