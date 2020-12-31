package com.sstudio.submission_made.core.domain.model


data class ChannelWithScheduleModel(
    var channel: Channel?,
    var schedule: List<Schedule>?
)