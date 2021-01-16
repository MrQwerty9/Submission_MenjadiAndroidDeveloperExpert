package com.sstudio.submission_made.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.di.AppScope
import com.sstudio.submission_made.ui.channel.ChannelViewModel
import com.sstudio.submission_made.ui.favorite.FavoriteViewModel
import com.sstudio.submission_made.ui.schedule.ScheduleViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(private val tvGuideUseCase: TvGuideUseCase) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChannelViewModel::class.java) -> {
                ChannelViewModel(tvGuideUseCase) as T
            }
            modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                ScheduleViewModel(tvGuideUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(tvGuideUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
