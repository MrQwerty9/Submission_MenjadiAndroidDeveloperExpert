package com.sstudio.submission_made.di

import com.sstudio.submission_made.core.domain.usecase.TvGuideInteractor
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideTourismUseCase(tvGuideInteractor: TvGuideInteractor): TvGuideUseCase

}