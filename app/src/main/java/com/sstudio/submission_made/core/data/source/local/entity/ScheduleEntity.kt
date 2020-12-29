package com.sstudio.submission_made.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["channelId", "date", "time"],
    foreignKeys = [ForeignKey(entity = ChannelEntity::class,
        parentColumns = ["id"],
        childColumns = ["channelId"])])
data class ScheduleEntity(
    val channelId: Int,
    val date: String,
    val time: String,
    val title: String
)