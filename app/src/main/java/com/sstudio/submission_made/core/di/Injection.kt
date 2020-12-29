package com.sstudio.submission_made.core.di

import android.content.Context
import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDatabase
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.data.source.remote.api.ApiConfig
import com.sstudio.submission_made.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): TvGuideRepository {
        val database = TvGuideDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.getApiService())
        val localDataSource = LocalDataSource.getInstance(database.tvGuideDao())
        val appExecutors = AppExecutors()

        return TvGuideRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}
