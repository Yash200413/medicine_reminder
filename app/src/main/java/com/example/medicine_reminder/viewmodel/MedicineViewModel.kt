package com.example.medicine_reminder.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.Repository
import com.example.medicine_reminder.data.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val medicines = repository.getAllMedicines()

    fun addMedicine(medicine: Medicine) = viewModelScope.launch {
        repository.insertMedicine(medicine)
    }

    fun updateMedicine(medicine: Medicine) = viewModelScope.launch {
        repository.updateMedicine(medicine)
    }

    fun deleteMedicine(medicine: Medicine) = viewModelScope.launch {
        repository.deleteMedicine(medicine)
    }

    fun addReminder(reminder: Reminder) = viewModelScope.launch {
        repository.insertReminder(reminder)
    }

    fun addLog(log: LogHistory) = viewModelScope.launch {
        repository.insertLog(log)
    }
}
