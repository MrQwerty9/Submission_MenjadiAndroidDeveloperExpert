package com.sstudio.submission_made.core.data.source.remote.response

data class ChannelResponse(
    val count: Int,
    val result: List<Result>
) {
    data class Result(
        val channel: String,
        val id: Int,
        val logoPath: String
    )
}