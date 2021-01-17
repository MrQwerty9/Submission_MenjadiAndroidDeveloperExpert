package com.sstudio.submission_made.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var listChannel: LiveData<PagedList<Channel>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideUseCase.getAllFavoriteChannel().toLiveData()
            }
            return field
        }
        private set
}