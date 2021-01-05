package com.sstudio.submission_made.di

import com.sstudio.submission_made.core.domain.usecase.TvGuideInteractor
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.ui.channel.ChannelViewModel
import com.sstudio.submission_made.ui.schedule.ScheduleViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TvGuideUseCase> { TvGuideInteractor(get()) }
}

val viewModelModule = module {
    viewModel { ChannelViewModel(get()) }
    viewModel { ScheduleViewModel(get()) }
}