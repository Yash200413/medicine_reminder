package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.medicine_reminder.data.repository.ReminderRepository
import com.example.medicine_reminder.utils.AlarmPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val repository: ReminderRepository,
    private val alarmPlayer: AlarmPlayer
) : ViewModel() {

    fun startAlarm() = alarmPlayer.startAlarm()
    fun stopAlarm() = alarmPlayer.stopAlarm()

    fun snooze(reminderId: Int, medicineName: String) {
        repository.scheduleSnooze(reminderId, medicineName)
        alarmPlayer.stopAlarm()
    }

    fun dismiss(reminderId: Int) {
        repository.dismiss(reminderId)
        alarmPlayer.stopAlarm()
    }
}
