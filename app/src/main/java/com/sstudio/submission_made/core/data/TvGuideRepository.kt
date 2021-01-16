package com.sstudio.submission_made.core.data

import androidx.paging.PagedList
import androidx.paging.toFlowable
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
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    override fun getAllChannel(needFetch: Boolean): Flowable<Resource<PagedList<Channel>>> {
        return object :
            NetworkBoundResource<PagedList<Channel>, ChannelResponse>() {
            override fun loadFromDB(): Flowable<PagedList<Channel>> {
                val groupItemFactory =
                    DataMapper.mapChannelEntitiesToDomain(localDataSource.getAllChannels())

                return groupItemFactory.toFlowable(4)
            }

            override fun shouldFetch(data: PagedList<Channel>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): Flowable<ApiResponse<ChannelResponse>> =
                remoteDataSource.getAllChannel()

            override fun saveCallResult(data: ChannelResponse) {
                localDataSource.insertAllChannel(DataMapper.mapChanelResponsesToEntities(data.result))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getSchedule(
        needFetch: Boolean,
        channelId: Int,
        date: String
    ): Flowable<Resource<List<ChannelWithScheduleModel>>> {
        return object : NetworkBoundResource<List<ChannelWithScheduleModel>, ScheduleResponse>() {
            override fun loadFromDB(): Flowable<List<ChannelWithScheduleModel>> {
                return localDataSource.getChannelWithScheduleById(channelId, date).map { scheduleList ->
                    scheduleList.map {
                        DataMapper.mapChannelScheduleEntitiesToDomain(it)
                    }
                }
            }

            override fun shouldFetch(data: List<ChannelWithScheduleModel>?): Boolean =
                data == null || data.isEmpty() || needFetch

            override fun createCall(): Flowable<ApiResponse<ScheduleResponse>> {
                val data = remoteDataSource.getSchedules(channelId, date)
                return data
            }

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
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    }
                }
            }
        }.asFlowable()
    }

    override fun getAllFavoriteChannel(): Flowable<PagedList<Channel>> {
        val favoriteEntity =
            DataMapper.mapChannelFavoriteToDomainPagedList(localDataSource.getAllFavoriteChannel())

        return favoriteEntity.toFlowable(4)
    }

    override fun setFavorite(channelId: Int) {
        localDataSource.insertFavorite(FavoriteEntity(channelId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun getFavoriteById(channelId: Int): Flowable<List<Favorite>> =
        localDataSource.getFavoriteById(channelId).map { favList ->
            favList.map { fav ->
                Favorite(fav.channelId)
            }
        }


    override fun deleteFavorite(channelId: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteTv(channelId) }
    }
}

