package com.sstudio.submission_made.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.vo.Resource

interface ITvGuideRepository {
    fun getAllChannel(needFetch: Boolean) : LiveData<Resource<PagedList<Channel>>>
    fun getSchedule(needFetch: Boolean, channelId: Int, date: String): LiveData<Resource<ChannelWithScheduleModel>>
    fun getAllFavoriteChannel(): LiveData<PagedList<Channel>>

    fun setFavorite(channelId: Int)
    fun getFavoriteById(channelId: Int): LiveData<Favorite>
    fun deleteFavorite(channelId: Int)
}