package com.example.medicine_reminder.utils

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

object AlarmPermissionHelper {

    fun checkAndRequestPermissions(context: Context) {
        if (!canScheduleExactAlarms(context)) {
            showExplanationDialog(
                context,
                title = "Allow Exact Alarms",
                message = "To make sure your medicine reminders ring on time, even in silent or DND mode, please allow exact alarms.",
            ) {
                requestExactAlarms(context)
            }
        }

        if (!isIgnoringBatteryOptimizations(context)) {
            showExplanationDialog(
                context,
                title = "Disable Battery Optimization",
                message = "To make sure reminders always go off, even when the phone is idle, please disable battery optimization for this app.",
            ) {
                requestIgnoreBatteryOptimizations(context)
            }
        }
    }

    // -------- Exact Alarm --------
    private fun canScheduleExactAlarms(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    private fun requestExactAlarms(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }

    // -------- Battery Optimization --------
    private fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isIgnoringBatteryOptimizations(context.packageName)
        } else {
            true
        }
    }

    private fun requestIgnoreBatteryOptimizations(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
        }
    }

    // -------- Dialog --------
    private fun showExplanationDialog(
        context: Context,
        title: String,
        message: String,
        onPositive: () -> Unit
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Allow") { _, _ -> onPositive() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
