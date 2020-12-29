package com.sstudio.submission_made.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.sstudio.submission_made.core.data.source.local.entity.*

@Dao
interface TvGuideDao {

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannels():  DataSource.Factory<Int, ChannelEntity>

    @Query("SELECT *, * FROM ChannelEntity, FavoriteEntity WHERE id = favoriteEntity.channelId")
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite>

    @Transaction
    @Query("SELECT * FROM ChannelEntity WHERE id = :channelId")
    fun getChannelById(channelId: Int): LiveData<ChannelWithSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllChannel(movie: List<ChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(data: ScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where channelId = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where channelId = :id")
    fun getFavoriteById(id: Int): LiveData<List<FavoriteEntity>>
}