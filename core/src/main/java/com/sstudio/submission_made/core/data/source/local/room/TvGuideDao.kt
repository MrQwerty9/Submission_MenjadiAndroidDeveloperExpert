package com.sstudio.submission_made.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.ChannelFavorite
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvGuideDao {

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannels():  DataSource.Factory<Int, ChannelEntity>

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannelsList():  Flow<List<ChannelEntity>>

    @Transaction
    @Query("SELECT *, * FROM ChannelEntity, FavoriteEntity WHERE ChannelEntity.id = favoriteEntity.channelId")
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite>

    @Query("SELECT ScheduleEntity.* FROM ScheduleEntity WHERE ScheduleEntity.channelId = :channelId AND ScheduleEntity.date = :date")
    fun getSchedule(channelId: Int, date: String): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChannel(movie: List<ChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(data: List<ScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where channelId = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where channelId = :id")
    fun getFavoriteById(id: Int): Flow<FavoriteEntity?>
}