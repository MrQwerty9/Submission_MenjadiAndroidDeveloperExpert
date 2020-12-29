package com.sstudio.submission_made.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity

@Database(entities = [ChannelEntity::class, ScheduleEntity::class, FavoriteEntity::class],
    version = 4,
    exportSchema = false)
abstract class TvGuideDatabase : RoomDatabase() {
    abstract fun tvGuideDao(): TvGuideDao

    companion object{
        @Volatile
        private var INSTANCE: TvGuideDatabase? = null

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE ScheduleEntity")
                database.execSQL("CREATE TABLE IF NOT EXISTS `ScheduleEntity` (`channelId` INTEGER NOT NULL, `date` TEXT NOT NULL, `time` TEXT NOT NULL, `title` TEXT NOT NULL, PRIMARY KEY(`channelId`, `date`, `time`), FOREIGN KEY(`channelId`) REFERENCES `ChannelEntity`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )")
            }
        }

        fun getInstance(context: Context): TvGuideDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    TvGuideDatabase::class.java,
                    "MovieTvDb.db")
                    .addMigrations(MIGRATION_3_4)
                    .build()
            }
    }


}