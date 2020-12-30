package com.sstudio.submission_made.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.sstudio.submission_made.core.data.source.local.entity.*
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDao

class LocalDataSource private constructor(private val mGuideDao: TvGuideDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(tvGuideDao: TvGuideDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(tvGuideDao)
    }

    fun getAllChannels(): DataSource.Factory<Int, ChannelEntity> = mGuideDao.getAllChannels()
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite> = mGuideDao.getAllFavoriteChannel()
    fun getChannelWithScheduleById(id: Int, date: String): LiveData<ChannelWithSchedule> = mGuideDao.getChannelById(id, date)

    fun insertAllChannel(channel: List<ChannelEntity>) = mGuideDao.insertAllChannel(channel)
    fun insertSchedule(schedule: ScheduleEntity) = mGuideDao.insertSchedule(schedule)

    fun insertFavorite(favorite: FavoriteEntity) = mGuideDao.insertFavorite(favorite)
    fun deleteFavoriteTv(id: Int) = mGuideDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): LiveData<FavoriteEntity> = mGuideDao.getFavoriteById(id)
}