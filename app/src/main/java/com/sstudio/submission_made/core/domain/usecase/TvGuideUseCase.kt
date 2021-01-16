package com.sstudio.submission_made.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.vo.Resource
import io.reactivex.Flowable

interface TvGuideUseCase {
    fun getAllChannel(needFetch: Boolean) : Flowable<Resource<PagedList<Channel>>>
    fun getSchedule(needFetch: Boolean, channelId: Int, date: String): Flowable<Resource<List<ChannelWithScheduleModel>>>
    fun getAllFavoriteChannel(): Flowable<PagedList<Channel>>

    fun setFavorite(channelId: Int)
    fun getFavoriteById(channelId: Int): Flowable<List<Favorite>>
    fun deleteFavorite(channelId: Int)
}