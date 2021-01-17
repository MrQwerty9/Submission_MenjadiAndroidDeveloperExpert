package com.sstudio.submission_made.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sstudio.submission_made.core.ui.ViewModelFactory
import com.sstudio.submission_made.ui.channel.ChannelViewModel
import com.sstudio.submission_made.ui.favorite.FavoriteViewModel
import com.sstudio.submission_made.ui.schedule.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelViewModel::class)
    abstract fun bindHomeViewModel(viewModel: ChannelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindDetailTourismViewModel(viewModel: ScheduleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}