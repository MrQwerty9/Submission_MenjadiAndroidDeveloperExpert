package com.sstudio.submission_made.core.data.source.remote

import android.util.Log
import com.sstudio.submission_made.core.data.source.remote.api.ApiService
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

//    companion object {
//        @Volatile
//        private var instance: RemoteDataSource? = null
//
//        fun getInstance(apiService: ApiService): RemoteDataSource =
//                instance ?: synchronized(this) {
//                    instance ?: RemoteDataSource(apiService)
//                }
//    }


    fun getAllChannel(): Flow<ApiResponse<ChannelResponse>> {
//        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getChannels()
                if (response.result.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
//                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
//                EspressoIdlingResource.decrement()
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSchedules(idChannel: Int, date: String): Flow<ApiResponse<ScheduleResponse>> {
//        EspressoIdlingResource.increment()
        return flow {
            try {
                val response = apiService.getChannelDetail(idChannel, date)
                if (response.result.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
//                EspressoIdlingResource.decrement()
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
//                EspressoIdlingResource.decrement()
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

