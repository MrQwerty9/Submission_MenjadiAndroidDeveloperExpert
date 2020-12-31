package com.sstudio.submission_made.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.sstudio.submission_made.core.data.source.local.entity.*
import com.sstudio.submission_made.core.data.source.local.room.TvGuideDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource private constructor(private val mGuideDao: TvGuideDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(tvGuideDao: TvGuideDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(tvGuideDao)
    }

    fun getAllChannels(): DataSource.Factory<Int, ChannelEntity> = mGuideDao.getAllChannels()
    fun getAllChannelsList(): Flow<List<ChannelEntity>> = mGuideDao.getAllChannelsList()
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite> = mGuideDao.getAllFavoriteChannel()
    fun getChannelWithScheduleById(id: Int, date: String): Flow<ChannelWithSchedule?> = mGuideDao.getChannelById(id, date)

    suspend fun insertAllChannel(channel: List<ChannelEntity>) = mGuideDao.insertAllChannel(channel)
    suspend fun insertSchedule(schedule: ScheduleEntity) = mGuideDao.insertSchedule(schedule)

    fun insertFavorite(favorite: FavoriteEntity) = mGuideDao.insertFavorite(favorite)
    fun deleteFavoriteTv(id: Int) = mGuideDao.deleteFavorite(id)
    fun getFavoriteById(id: Int): Flow<FavoriteEntity?> = mGuideDao.getFavoriteById(id)
}