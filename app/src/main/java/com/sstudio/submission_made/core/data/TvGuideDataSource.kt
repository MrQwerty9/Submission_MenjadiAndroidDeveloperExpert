package com.sstudio.submission_made.core.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.sstudio.submission_made.core.data.source.local.entity.*
import com.sstudio.submission_made.vo.Resource

internal interface TvGuideDataSource {
    fun getAllChannel(needFetch: Boolean) : LiveData<Resource<PagedList<ChannelEntity>>>
    fun getSchedule(needFetch: Boolean, channelId: Int, date: String): LiveData<Resource<ChannelWithSchedule>>
    fun getAllFavoriteChannel(): LiveData<PagedList<ChannelFavorite>>

    fun setFavorite(channelId: Int)
    fun getFavoriteById(channelId: Int): LiveData<List<FavoriteEntity>>
    fun deleteFavoriteTv(channelId: Int)
}