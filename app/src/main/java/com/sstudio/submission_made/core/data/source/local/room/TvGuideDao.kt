package com.sstudio.submission_made.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sstudio.submission_made.core.data.source.local.entity.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvGuideDao {

    @Query("SELECT * FROM ChannelEntity")
    fun getAllChannels():  DataSource.Factory<Int, ChannelEntity>

    @Query("SELECT *, * FROM ChannelEntity, FavoriteEntity WHERE id = favoriteEntity.channelId")
    fun getAllFavoriteChannel(): DataSource.Factory<Int, ChannelFavorite>

    @Query("SELECT *, * FROM ChannelEntity, ScheduleEntity WHERE id = :channelId AND date = :date")
    fun getChannelById(channelId: Int, date: String): Flowable<List<ChannelWithSchedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllChannel(movie: List<ChannelEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(data: ScheduleEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteEntity): Completable

    @Query("DELETE FROM FavoriteEntity where channelId = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM FavoriteEntity where channelId = :id")
    fun getFavoriteById(id: Int): Flowable<List<FavoriteEntity>>
}