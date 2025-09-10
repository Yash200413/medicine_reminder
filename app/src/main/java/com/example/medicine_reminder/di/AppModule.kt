package com.example.medicine_reminder.di


import android.content.Context
import androidx.room.Room
import com.example.medicine_reminder.data.local.AppDatabase
import com.example.medicine_reminder.data.local.Repository
import com.example.medicine_reminder.data.local.dao.LogHistoryDao
import com.example.medicine_reminder.data.local.dao.MedicineDao
import com.example.medicine_reminder.data.local.dao.ReminderDao
import com.example.medicine_reminder.reminder.ReminderScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "medicine_db").build()

    @Provides
    fun provideMedicineDao(db: AppDatabase): MedicineDao = db.medicineDao()

    @Provides
    fun provideReminderDao(db: AppDatabase): ReminderDao = db.reminderDao()

    @Provides
    fun provideLogHistoryDao(db: AppDatabase): LogHistoryDao = db.logHistoryDao()

    @Provides
    @Singleton
    fun provideRepository(
        medicineDao: MedicineDao,
        reminderDao: ReminderDao,
        logHistoryDao: LogHistoryDao
    ): Repository = Repository(medicineDao, reminderDao, logHistoryDao)

    @Provides @Singleton
    fun provideScheduler(@ApplicationContext context: Context): ReminderScheduler =
        ReminderScheduler(context)
}
