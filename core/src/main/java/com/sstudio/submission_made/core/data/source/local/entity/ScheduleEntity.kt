package com.sstudio.submission_made.core.data.source.local.entity

import androidx.room.Entity

@Entity(
    primaryKeys = ["scheduleId", "channelId"])
data class ScheduleEntity(
    val scheduleId: Int,
    val channelId: Int,
    val date: String,
    val time: String,
    val title: String,
    var reminder: Boolean
)