package com.sstudio.submission_made.core.di

import android.content.Context
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository() : ITvGuideRepository
}