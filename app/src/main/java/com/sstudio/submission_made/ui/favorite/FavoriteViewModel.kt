package com.sstudio.submission_made.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submission_made.vo.Resource
import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.data.source.local.entity.ChannelFavorite
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule

class FavoriteViewModel(private val tvGuideRepository: TvGuideRepository) : ViewModel() {

    var listChannel: LiveData<PagedList<ChannelFavorite>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideRepository.getAllFavoriteChannel()
            }
            return field
        }
        private set
}