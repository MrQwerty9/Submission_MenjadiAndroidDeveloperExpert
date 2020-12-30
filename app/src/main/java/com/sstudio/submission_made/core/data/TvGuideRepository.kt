package com.sstudio.submission_made.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.data.source.remote.network.ApiResponse
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.repository.ITvGuideRepository
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.core.utils.DataMapper
import com.sstudio.submission_made.vo.Resource

class TvGuideRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ITvGuideRepository {

    companion object {
        @Volatile
        private var instance: ITvGuideRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): ITvGuideRepository =
            instance ?: synchronized(this) {
                instance ?: TvGuideRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllChannel(needFetch: Boolean): LiveData<Resource<PagedList<Channel>>> {
        return object :
            NetworkBoundResource<PagedList<Channel>, ChannelResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<Channel>> {
                val groupItemFactory = DataMapper.mapChannelEntitiesToDomain(localDataSource.getAllChannels())

                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(
                    groupItemFactory, config
                ).build()
            }

            override fun shouldFetch(data: PagedList<Channel>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): LiveData<ApiResponse<ChannelResponse>> =
                remoteDataSource.getAllChannel()

            override fun saveCallResult(data: ChannelResponse) {
                localDataSource.insertAllChannel(DataMapper.mapChanelResponsesToEntities(data.result))
            }
        }.asLiveData()
    }

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ): LiveData<Resource<ChannelWithScheduleModel>> {
        return object : NetworkBoundResource<ChannelWithScheduleModel, ScheduleResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<ChannelWithScheduleModel> {
                return Transformations.map(localDataSource.getChannelWithScheduleById(channelId, date)) {
                    DataMapper.mapChannelScheduleEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: ChannelWithScheduleModel?): Boolean =
                data?.schedule?.isEmpty() == true || data == null || needFetch

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
                }
            }
        }.asLiveData()
    }

    override fun getAllFavoriteChannel(): LiveData<PagedList<Channel>> {
        val favoriteEntity = DataMapper.mapChannelFavoriteToDomainPagedList(localDataSource.getAllFavoriteChannel())

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(
            favoriteEntity, config
        ).build()
    }

    override fun setFavorite(channelId: Int) {
        appExecutors.diskIO().execute { localDataSource.insertFavorite(FavoriteEntity(channelId)) }
    }

    override fun getFavoriteById(channelId: Int): LiveData<Favorite> =

        Transformations.map(localDataSource.getFavoriteById(channelId)){
            it?.let { favoriteEntity ->
                Favorite(favoriteEntity.channelId)
            }
        }


    override fun deleteFavorite(channelId: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteTv(channelId) }
    }
}

