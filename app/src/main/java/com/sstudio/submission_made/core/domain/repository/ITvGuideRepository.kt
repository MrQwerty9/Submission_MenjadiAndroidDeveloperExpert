package com.sstudio.submission_made.core.domain.repository

import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ITvGuideRepository {
    fun getAllChannel(needFetch: Boolean) : Flow<Resource<PagedList<Channel>>>
    fun getSchedule(needFetch: Boolean, channelId: Int, date: String): Flow<Resource<ChannelWithScheduleModel>>
    fun getAllFavoriteChannel(): Flow<PagedList<Channel>>

    fun setFavorite(channelId: Int)
    fun getFavoriteById(channelId: Int): Flow<Favorite?>
    fun deleteFavorite(channelId: Int)
}