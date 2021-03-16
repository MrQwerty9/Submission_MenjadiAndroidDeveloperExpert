package com.sstudio.submission_made.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @field:SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @field:SerializedName("id")
        val id: Int = 0,
        @field:SerializedName("channel_id")
        val channelId: Int = 0,
        @field:SerializedName("date")
        val date: String = "",
        @field:SerializedName("time")
        val time: String = "",
        @field:SerializedName("title")
        val title: String = "",
        @field:SerializedName("created_at")
        val createdAt: String? = "",
        @field:SerializedName("updated_at")
        val updatedAt: String? = ""
    )
}