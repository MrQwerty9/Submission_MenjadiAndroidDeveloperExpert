package com.sstudio.submission_made.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase

class FavoriteViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var listChannel: LiveData<PagedList<Channel>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideUseCase.getAllFavoriteChannel().asLiveData()
            }
            return field
        }
        private set
}