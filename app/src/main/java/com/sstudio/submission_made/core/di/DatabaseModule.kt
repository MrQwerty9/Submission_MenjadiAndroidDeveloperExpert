package com.sstudio.submission_made.core.di

import android.content.Context
import androidx.room.Room
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDao
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): TvGuideDatabase = Room.databaseBuilder(
        context,
        TvGuideDatabase::class.java, "MovieTvDb.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: TvGuideDatabase): TvGuideDao = database.tvGuideDao()
}