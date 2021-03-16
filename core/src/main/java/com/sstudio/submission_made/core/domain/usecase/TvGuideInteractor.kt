package com.sstudio.submission_made.core.domain.usecase

import com.sstudio.submission_made.core.domain.model.Schedule
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository

class TvGuideInteractor(private val iTvGuideRepository: ITvGuideRepository): TvGuideUseCase {
    override fun getAllChannel(needFetch: Boolean) = iTvGuideRepository.getAllChannel(needFetch)

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ) = iTvGuideRepository.getSchedule(needFetch, channelId, date)

    override fun getAllFavoriteChannel() = iTvGuideRepository.getAllFavoriteChannel()

    override fun setFavorite(channelId: Int) {
        iTvGuideRepository.setFavorite(channelId)
    }

    override fun getFavoriteById(channelId: Int) =
        iTvGuideRepository.getFavoriteById(channelId)

    override fun deleteFavorite(channelId: Int) {
        iTvGuideRepository.deleteFavorite(channelId)
    }

    override fun setReminder(schedule: Schedule) {
        iTvGuideRepository.setReminder(schedule)
    }
}