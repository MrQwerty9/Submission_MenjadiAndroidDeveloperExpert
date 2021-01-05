package com.sstudio.submission_made.core.data.source.remote.response

data class ScheduleResponse(
    val count: Int,
    val result: List<Result>
) {
    data class Result(
        val showTimes: List<ShowTimes>,
        val date: String,
        val channelId: Int
    ) {
        data class ShowTimes(
            val time: String,
            val title: String
        )
    }
}