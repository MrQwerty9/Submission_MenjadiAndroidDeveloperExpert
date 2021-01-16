package com.sstudio.submission_made.core.domain.usecase

import androidx.paging.PagedList
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository
import com.sstudio.submission_made.vo.Resource
import io.reactivex.Flowable

class TvGuideInteractor(private val iTvGuideRepository: ITvGuideRepository): TvGuideUseCase {
    override fun getAllChannel(needFetch: Boolean): Flowable<Resource<PagedList<Channel>>> =
        iTvGuideRepository.getAllChannel(needFetch)

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ): Flowable<Resource<List<ChannelWithScheduleModel>>> =
        iTvGuideRepository.getSchedule(needFetch, channelId, date)

    override fun getAllFavoriteChannel(): Flowable<PagedList<Channel>> =
        iTvGuideRepository.getAllFavoriteChannel()

    override fun setFavorite(channelId: Int) {
        iTvGuideRepository.setFavorite(channelId)
    }

    override fun getFavoriteById(channelId: Int): Flowable<List<Favorite>> =
        iTvGuideRepository.getFavoriteById(channelId)

    override fun deleteFavorite(channelId: Int) {
        iTvGuideRepository.deleteFavorite(channelId)
    }
}