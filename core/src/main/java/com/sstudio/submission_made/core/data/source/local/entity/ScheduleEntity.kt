package com.sstudio.submission_made.core.data.source.local.entity

import androidx.room.Entity

@Entity(
    primaryKeys = ["scheduleId", "channelId", "date"],
    foreignKeys = [androidx.room.ForeignKey(
        entity = ChannelEntity::class,
        parentColumns = ["id"],
        childColumns = ["channelId"]
    )])
data class ScheduleEntity(
    val scheduleId: Int,
    val channelId: Int,
    val date: String,
    val time: String,
    val title: String
)