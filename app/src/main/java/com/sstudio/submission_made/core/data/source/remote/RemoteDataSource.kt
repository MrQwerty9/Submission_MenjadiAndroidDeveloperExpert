package com.sstudio.submission_made.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sstudio.submission_made.core.data.source.remote.api.ApiService
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(apiService)
                }
    }


    fun getAllChannel(): LiveData<ApiResponse<ChannelResponse>> {
//        EspressoIdlingResource.increment()
        val resultChannels = MutableLiveData<ApiResponse<ChannelResponse>>()
        apiService.getChannels()
            .enqueue(object : Callback<ChannelResponse> {
            override fun onResponse(
                call: Call<ChannelResponse>,
                response: Response<ChannelResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { resultChannels.value = ApiResponse.Success(it) }
                } else {
                    Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                }
//                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ChannelResponse>, t: Throwable?) {
                Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
//                EspressoIdlingResource.decrement()
            }
        })
        return resultChannels
    }

    fun getSchedules(idChannel: Int, date: String): LiveData<ApiResponse<ScheduleResponse>> {
//        EspressoIdlingResource.increment()
        val resultSchedule = MutableLiveData<ApiResponse<ScheduleResponse>>()
        apiService.getChannelDetail(idChannel, date)
            .enqueue(object : Callback<ScheduleResponse> {
                override fun onResponse(
                    call: Call<ScheduleResponse>,
                    response: Response<ScheduleResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { resultSchedule.value = ApiResponse.Success(it) }
                    } else {
                        Log.e("RemoteDataSource", "onFailure: ${response.message()}")
                    }
//                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<ScheduleResponse>, t: Throwable?) {
                    Log.e("RemoteDataSource", "onFailure: ${t?.message.toString()}")
//                    EspressoIdlingResource.decrement()
                }
            })
        return resultSchedule
    }
}

