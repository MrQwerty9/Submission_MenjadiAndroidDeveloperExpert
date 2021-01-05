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

//    companion object{
//        @Volatile
//        private var INSTANCE: TvGuideDatabase? = null
//
//        private val MIGRATION_5_6 = object : Migration(5, 6) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("DROP TABLE FavoriteEntity")
//                database.execSQL("CREATE TABLE IF NOT EXISTS `FavoriteEntity` (`channelId` INTEGER NOT NULL, PRIMARY KEY(`channelId`))")
//            }
//        }
//
//        fun getInstance(context: Context): TvGuideDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
//                    TvGuideDatabase::class.java,
//                    "MovieTvDb.db")
//                    .addMigrations(MIGRATION_5_6)
//                    .build()
//            }
//    }


}