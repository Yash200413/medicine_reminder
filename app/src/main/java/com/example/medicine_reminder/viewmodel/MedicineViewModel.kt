package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.local.Repository
import com.example.medicine_reminder.data.local.entity.Medicine
import com.example.medicine_reminder.data.local.entity.Reminder
import com.example.medicine_reminder.reminder.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: Repository,
    private val scheduler: ReminderScheduler
) : ViewModel() {

    // -------------------- Combined UI State -------------------- //
    private val _medicines = MutableStateFlow<List<MedicineWithReminders>>(emptyList())
    val medicines: StateFlow<List<MedicineWithReminders>> = _medicines.asStateFlow()

    init {
        observeMedicinesAndReminders()
    }

    private fun observeMedicinesAndReminders() {
        viewModelScope.launch {
            combine(
                repository.getAllMedicinesFlow(),
                repository.getAllRemindersFlow()
            ) { meds, rems ->
                meds.map { med ->
                    val associatedReminders = rems.filter { it.medicineOwnerId == med.medicineId }
                    MedicineWithReminders(med, associatedReminders)
                }
            }.collect { combined ->
                _medicines.value = combined
            }
        }
    }

    // -------------------- Wrapper Data Class -------------------- //
    data class MedicineWithReminders(
        val medicine: Medicine,
        val reminders: List<Reminder>
    )

    // -------------------- CRUD Operations -------------------- //

    fun addMedicine(medicine: Medicine, reminders: List<String>) {
        viewModelScope.launch {
            val medicineId = repository.insertMedicine(medicine)
            reminders.forEach { time ->
                val rem = Reminder(time = time, medicineOwnerId = medicineId)
                val reminderId = repository.insertReminder(rem)
                scheduler.scheduleReminder(medicine.copy(medicineId = medicineId), rem.copy(reminderId = reminderId))
            }
        }
    }

    fun updateMedicine(medicine: Medicine) {
        viewModelScope.launch { repository.updateMedicine(medicine) }
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            val reminders = repository.getRemindersForMedicine(medicine.medicineId)
            reminders.forEach { scheduler.cancelReminder(it.reminderId) }
            repository.deleteMedicine(medicine)
        }
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            val reminderId = repository.insertReminder(reminder)
            val med = repository.getMedicineById(reminder.medicineOwnerId)
            med?.let { scheduler.scheduleReminder(it, reminder.copy(reminderId = reminderId)) }
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            scheduler.cancelReminder(reminder.reminderId)
            repository.updateReminder(reminder)
            repository.getMedicineById(reminder.medicineOwnerId)?.let {
                scheduler.scheduleReminder(it, reminder)
            }
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            scheduler.cancelReminder(reminder.reminderId)
            repository.deleteReminder(reminder)
        }
    }
}
