package com.sstudio.submission_made.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.*
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.vo.Resource

class TvGuideRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : TvGuideDataSource {

    companion object {
        @Volatile
        private var instance: TvGuideRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): TvGuideRepository =
            instance ?: synchronized(this) {
                instance ?: TvGuideRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllChannel(needFetch: Boolean): LiveData<Resource<PagedList<ChannelEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ChannelEntity>, ChannelResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<ChannelEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllChannels(), config).build()
            }

            override fun shouldFetch(data: PagedList<ChannelEntity>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): LiveData<ApiResponse<ChannelResponse>> =
                remoteDataSource.getAllChannel()

            override fun saveCallResult(data: ChannelResponse) {
                val channelList = ArrayList<ChannelEntity>()
                for (response in data.result) {
                    val movie = ChannelEntity(
                        response.id,
                        response.channel,
                        response.logoPath
                    )
                    channelList.add(movie)
                }
                localDataSource.insertAllChannel(channelList)
            }
        }.asLiveData()
    }

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ): LiveData<Resource<ChannelWithSchedule>> {
        return object : NetworkBoundResource<ChannelWithSchedule, ScheduleResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<ChannelWithSchedule> =
                localDataSource.getChannelWithScheduleById(channelId)

            override fun shouldFetch(data: ChannelWithSchedule?): Boolean =
                data?.scheduleEntity?.isEmpty() == true || data == null || needFetch

            override fun createCall(): LiveData<ApiResponse<ScheduleResponse>> =
                remoteDataSource.getSchedules(channelId, date)

            override fun saveCallResult(data: ScheduleResponse) {
                for (response in data.result) {
                    val showTimesSort = response.showTimes.sortedBy {
                        it.time
                    }
                    for (showTimes in showTimesSort) {
                        localDataSource.insertSchedule(
                            ScheduleEntity(
                                response.channelId,
                                response.date,
                                showTimes.time,
                                showTimes.title
                            )
                        )
                    }
                    Log.d("mytag", "repository $response")
                }
            }
        }.asLiveData()
    }

    override fun getAllFavoriteChannel(): LiveData<PagedList<ChannelFavorite>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getAllFavoriteChannel(), config).build()
    }

    override fun setFavorite(channelId: Int) {
        appExecutors.diskIO().execute{ localDataSource.insertFavorite(FavoriteEntity(channelId)) }
    }

    override fun getFavoriteById(channelId: Int): LiveData<List<FavoriteEntity>>  = localDataSource.getFavoriteById(channelId)

    override fun deleteFavoriteTv(channelId: Int) {
        appExecutors.diskIO().execute{localDataSource.deleteFavoriteTv(channelId)}
    }
}

