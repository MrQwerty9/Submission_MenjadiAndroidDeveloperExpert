package com.sstudio.submission_made.ui.channel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sstudio.submission_made.vo.Resource
import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity

class ChannelViewModel(private val tvGuideRepository: TvGuideRepository) : ViewModel() {

    var listChannel: LiveData<Resource<PagedList<ChannelEntity>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideRepository.getAllChannel(false)
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listChannel = tvGuideRepository.getAllChannel(true)
    }
}