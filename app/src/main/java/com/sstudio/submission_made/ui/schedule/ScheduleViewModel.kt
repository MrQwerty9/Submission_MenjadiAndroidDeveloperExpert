package com.sstudio.submission_made.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase

class ScheduleViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    fun setFavorite(id: Int) =
        tvGuideUseCase.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<Favorite> =
        tvGuideUseCase.getFavoriteById(id).asLiveData()

    fun deleteFavorite(id: Int){
        tvGuideUseCase.deleteFavorite(id)
    }
}