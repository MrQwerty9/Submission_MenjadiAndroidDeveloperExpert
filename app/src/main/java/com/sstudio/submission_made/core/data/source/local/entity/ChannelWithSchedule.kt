package com.sstudio.submission_made.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class ChannelWithSchedule(
    @Embedded
    var channelEntity: ChannelEntity,

    @Relation(parentColumn = "id", entityColumn = "channelId")
    @NonNull
    var scheduleEntity: List<ScheduleEntity>
)