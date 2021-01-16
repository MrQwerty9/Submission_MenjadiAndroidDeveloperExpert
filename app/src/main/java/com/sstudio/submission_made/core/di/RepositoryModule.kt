package com.sstudio.submission_made.core.di

import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(tvGuideRepository: TvGuideRepository): ITvGuideRepository

}