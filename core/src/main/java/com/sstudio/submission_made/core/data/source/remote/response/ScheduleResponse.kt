package com.sstudio.submission_made.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @field:SerializedName("showTimes")
        val showTimes: List<ShowTimes>,
        @field:SerializedName("date")
        val date: String,
        @field:SerializedName("channelId")
        val channelId: Int
    ) {
        data class ShowTimes(
            @field:SerializedName("time")
            val time: String,
            @field:SerializedName("title")
            val title: String
        )
    }
}