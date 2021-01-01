package com.sstudio.submission_made.favorite.di

import com.sstudio.submission_made.core.domain.usecase.TvGuideInteractor
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.favorite.FavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TvGuideUseCase> { TvGuideInteractor(get()) }
}

val viewModelModule = module {
    viewModel { FavoriteViewModel(get()) }
}