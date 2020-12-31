package com.sstudio.submission_made.ui.channel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.vo.Resource

class ChannelViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var listChannel: LiveData<Resource<PagedList<Channel>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideUseCase.getAllChannel(false).asLiveData()
            }
            return field
        }
        private set

    fun fetchListMovie(){
        listChannel = tvGuideUseCase.getAllChannel(true).asLiveData()
    }
}