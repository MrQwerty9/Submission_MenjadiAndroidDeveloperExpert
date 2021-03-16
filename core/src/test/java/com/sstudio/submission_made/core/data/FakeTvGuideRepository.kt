package com.sstudio.submission_made.core.data

import android.util.Log
import androidx.lifecycle.asFlow
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.model.Schedule
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeTvGuideRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITvGuideRepository {

    override fun getAllChannel(needFetch: Boolean): Flow<Resource<PagedList<Channel>>> {
        return object :
            NetworkBoundResource<PagedList<Channel>, ChannelResponse>() {
            override fun loadFromDB(): Flow<PagedList<Channel>> {

                val groupItemFactory =
                    DataMapper.mapChannelEntitiesToDomain(localDataSource.getAllChannels())

                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()

                return LivePagedListBuilder(
                    groupItemFactory, config
                ).build().asFlow()
            }

            override fun shouldFetch(data: PagedList<Channel>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override suspend fun createCall(): Flow<ApiResponse<ChannelResponse>> =
                remoteDataSource.getAllChannel()
//                remoteDataSource.getTest().asFlow()

            override suspend fun saveCallResult(data: ChannelResponse) {
                localDataSource.insertAllChannel(DataMapper.mapChanelResponsesToEntities(data.result))
            }
        }.asFlow()
    }

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ): Flow<Resource<List<Schedule>>> {
        return object : NetworkBoundResource<List<Schedule>, ScheduleResponse>() {
            override fun loadFromDB(): Flow<List<Schedule>> {
                return localDataSource.getSchedule(channelId, date).map {
                    Log.d("mytag", "$date $channelId $it")
                    DataMapper.mapScheduleEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Schedule>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override suspend fun createCall(): Flow<ApiResponse<ScheduleResponse>> =
                remoteDataSource.getSchedules(channelId, date)

            override suspend fun saveCallResult(data: ScheduleResponse) {

                    localDataSource.insertSchedule(
                        DataMapper.mapScheduleResponseToEntity(data)
                    )
            }
        }.asFlow()
    }

    override fun getAllFavoriteChannel(): Flow<PagedList<Channel>> {
        val favoriteEntity =
            DataMapper.mapChannelFavoriteToDomainPagedList(localDataSource.getAllFavoriteChannel())

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(
                favoriteEntity, config
            ).build().asFlow()
    }

    override fun setFavorite(channelId: Int) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(FavoriteEntity(channelId)) }
    }

    override fun getFavoriteById(channelId: Int): Flow<Favorite> =
        localDataSource.getFavoriteById(channelId).let {
            it.map { favoriteEntity ->
                Favorite(favoriteEntity?.channelId)
            }
        }

    override fun deleteFavorite(channelId: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteTv(channelId) }
    }
}

