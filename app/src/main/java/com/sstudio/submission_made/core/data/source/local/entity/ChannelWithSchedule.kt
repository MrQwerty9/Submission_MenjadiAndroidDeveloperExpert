package com.sstudio.submission_made.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ChannelWithSchedule(
    @Embedded
    var channelEntity: ChannelEntity,

    @Relation(parentColumn = "id", entityColumn = "channelId")
    var scheduleEntity: List<ScheduleEntity>
)