package com.example.medicine_reminder.di

import android.content.Context
import com.example.medicine_reminder.utils.AlarmPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {

    @Provides
    @Singleton
    fun provideAlarmPlayer(@ApplicationContext context: Context): AlarmPlayer {
        return AlarmPlayer(context)
    }
}
