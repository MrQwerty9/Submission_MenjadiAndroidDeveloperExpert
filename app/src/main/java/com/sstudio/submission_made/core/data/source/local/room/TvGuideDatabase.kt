package com.sstudio.submission_made.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity

@Database(entities = [ChannelEntity::class, ScheduleEntity::class, FavoriteEntity::class],
    version = 6,
    exportSchema = false)
abstract class TvGuideDatabase : RoomDatabase() {
    abstract fun tvGuideDao(): TvGuideDao
}