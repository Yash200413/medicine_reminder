package com.example.medicine_reminder.di


import android.content.Context
import androidx.room.Room
import com.example.medicine_reminder.data.AppDatabase
import com.example.medicine_reminder.data.Repository
import com.example.medicine_reminder.data.dao.*
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
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }

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
}
