package com.sstudio.submission_made.core.data.source.local.entity

import androidx.room.Embedded

data class ChannelWithSchedule(
    @Embedded
    var scheduleEntity: List<ScheduleEntity>
)