package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.local.Repository
import com.example.medicine_reminder.data.repository.ReminderRepository
import com.example.medicine_reminder.reminder.ReminderScheduler
import com.example.medicine_reminder.utils.AlarmPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository,
    private val repository: Repository,
    private val alarmPlayer: AlarmPlayer,
    private val scheduler: ReminderScheduler
) : ViewModel() {

    fun stopAlarm() = alarmPlayer.stopAlarm()

    fun snooze(reminderId: Int, medicineName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.scheduleSnooze(reminderId, medicineName)

            // Stop alarm safely on main thread
            launch(Dispatchers.Main) {
                alarmPlayer.stopAlarm()
            }
        }
    }

    fun dismiss(reminderId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = repository.getReminderById(reminderId) ?: return@launch
            val medicine = repository.getMedicineById(reminder.medicineOwnerId)

            if (medicine != null) {
                scheduler.rescheduleReminderForNextDay(medicine, reminder)
                reminderRepository.dismiss(reminderId)
            }

            // Stop alarm safely on main thread
            launch(Dispatchers.Main) {
                stopAlarm()
            }
        }
    }
}
