package com.sstudio.submission_made.core.domain.repository

import androidx.paging.PagedList
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ITvGuideRepository {
    fun getAllChannel(needFetch: Boolean) : Flow<Resource<PagedList<Channel>>>
    fun getSchedule(needFetch: Boolean, channelId: Int, date: String): Flow<Resource<List<Schedule>>>
    fun getAllFavoriteChannel(): Flow<PagedList<Channel>>

    fun setFavorite(channelId: Int)
    fun getFavoriteById(channelId: Int): Flow<Favorite>
    fun deleteFavorite(channelId: Int)
}