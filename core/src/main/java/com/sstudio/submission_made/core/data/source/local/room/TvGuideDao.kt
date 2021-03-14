package com.sstudio.submission_made.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.sstudio.submission_made.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TvGuideDao {

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannels():  DataSource.Factory<Int, ChannelEntity>

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannelsList():  Flow<List<ChannelEntity>>

    @Query("SELECT *, * FROM ChannelEntity, FavoriteEntity WHERE ChannelEntity.id = favoriteEntity.channelId")
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite>

    @Transaction
    @Query("SELECT *, * FROM ChannelEntity, ScheduleEntity WHERE ChannelEntity.id = :channelId AND ScheduleEntity.date = '2021-03-14'")
    fun getChannelById(channelId: Int): Flow<ChannelWithSchedule?>

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