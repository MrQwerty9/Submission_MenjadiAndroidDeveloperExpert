package com.sstudio.submission_made.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.di.AppScope
import com.sstudio.submission_made.ui.channel.ChannelViewModel
import com.sstudio.submission_made.ui.favorite.FavoriteViewModel
import com.sstudio.submission_made.ui.schedule.ScheduleViewModel
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return creator.get() as T
    }
}
