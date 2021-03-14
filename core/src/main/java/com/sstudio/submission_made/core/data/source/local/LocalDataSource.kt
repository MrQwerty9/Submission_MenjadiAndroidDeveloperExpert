package com.sstudio.submission_made.core.data.source.local

import androidx.paging.DataSource
import com.sstudio.submission_made.core.data.source.local.entity.*
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mGuideDao: TvGuideDao) {

    fun getAllChannels(): DataSource.Factory<Int, ChannelEntity> = mGuideDao.getAllChannels()
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite> = mGuideDao.getAllFavoriteChannel()
    fun getChannelWithScheduleById(id: Int, date: String): Flow<ChannelWithSchedule?> = mGuideDao.getChannelById(id)

    suspend fun insertAllChannel(channel: List<ChannelEntity>) = mGuideDao.insertAllChannel(channel)
    suspend fun insertSchedule(schedule: List<ScheduleEntity>) = mGuideDao.insertSchedule(schedule)

    fun insertFavorite(favorite: FavoriteEntity) = mGuideDao.insertFavorite(favorite)
    fun deleteFavoriteTv(id: Int) = mGuideDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): Flow<FavoriteEntity?> = mGuideDao.getFavoriteById(id)
}