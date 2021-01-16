package com.sstudio.submission_made.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.sstudio.submission_made.core.data.source.remote.api.ApiService
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(apiService)
                }
    }

    @SuppressLint("CheckResult")
    fun getAllChannel(): Flowable<ApiResponse<ChannelResponse>> {
        val resultData = PublishSubject.create<ApiResponse<ChannelResponse>>()
//        EspressoIdlingResource.increment()
        apiService.getChannels()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                resultData.onNext(if (response.result.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getSchedules(idChannel: Int, date: String): Flowable<ApiResponse<ScheduleResponse>> {
//        EspressoIdlingResource.increment()
        val resultSchedule = PublishSubject.create<ApiResponse<ScheduleResponse>>()
        apiService.getChannelDetail(idChannel, date)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe ({ response ->
                resultSchedule.onNext(if (response.result.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty)
            }, { error ->
                resultSchedule.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        return resultSchedule.toFlowable(BackpressureStrategy.BUFFER)
    }
}

