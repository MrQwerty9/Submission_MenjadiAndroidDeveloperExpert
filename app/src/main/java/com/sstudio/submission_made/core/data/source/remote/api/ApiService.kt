package com.sstudio.submission_made.core.data.source.remote.api

import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("channel/list")
    fun getChannels(): Call<ChannelResponse>

    @GET("channel/detail")
    fun getChannelDetail(
        @Query("id") channelId: Int,
        @Query("date") date: String
    ): Call<ScheduleResponse>
}