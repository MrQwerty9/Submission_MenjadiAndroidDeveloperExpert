package com.sstudio.submission_made.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ChannelResponse(
    @field:SerializedName("count")
    val count: Int,
    @field:SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @field:SerializedName("channel")
        val channel: String,
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("logoPath")
        val logoPath: String
    )
}