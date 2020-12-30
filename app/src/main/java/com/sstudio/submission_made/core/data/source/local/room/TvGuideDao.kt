package com.sstudio.submission_made.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sstudio.submission_made.core.data.source.local.entity.*

@Dao
interface TvGuideDao {

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannels():  DataSource.Factory<Int, ChannelEntity>

    @Query("SELECT *, * FROM ChannelEntity, FavoriteEntity WHERE id = favoriteEntity.channelId")
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite>

    @Query("SELECT *, * FROM ChannelEntity, ScheduleEntity WHERE id = :channelId AND date = :date")
    fun getChannelById(channelId: Int, date: String): LiveData<ChannelWithSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllChannel(movie: List<ChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(data: ScheduleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM FavoriteEntity where channelId = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where channelId = :id")
    fun getFavoriteById(id: Int): LiveData<FavoriteEntity>
}