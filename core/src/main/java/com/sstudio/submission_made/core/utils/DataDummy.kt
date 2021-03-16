package com.sstudio.submission_made.core.utils

import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.data.source.remote.response.ScheduleResponse
import java.util.*

object DataDummy {
    private const val dummyTotalPage = 99

    fun generateDummyChannel(): List<ChannelEntity> {

        val channel = ArrayList<ChannelEntity>()
        channel.add(
            ChannelEntity(1, "HBO", "http"
            )
        )
        channel.add(
            ChannelEntity(2, "Fox", "http"
            )
        )
        return channel
    }

    fun generateDummySchedule(channelId: Int, date: String): List<ScheduleEntity> {
        val schedules = ArrayList<ScheduleEntity>()
        schedules.add(
            ScheduleEntity(0, channelId, date, "19:00", "Fast and Forious", false)
        )
        schedules.add(
            ScheduleEntity(1, channelId, date, "19:00", "Need For", false)
        )
        return schedules
    }
//
//    fun generateDummyChannelWithSchedule(channelId: Int, date: String): ChannelWithSchedule {
//        val channel = generateDummyChannel()[0]
//        val schedule = generateDummySchedule(channelId, date)
//        return ChannelWithSchedule(channel, schedule)
//    }

    fun generateRemoteDummyChannel(): List<ChannelResponse.Result> {
        val channel = ArrayList<ChannelResponse.Result>()
        channel.add(
            ChannelResponse.Result("HBO", 1, "http")
        )
        channel.add(
            ChannelResponse.Result("Fox", 2, "http")
        )
        return channel
    }

    fun generateRemoteDummySchedule(channelId: Int, date: String): List<ScheduleResponse.Result> {
        val schedule = ArrayList<ScheduleResponse.Result>()
        schedule.add(
            ScheduleResponse.Result(0, channelId, date, "19:00:00", "Fast and Forious")
        )
        schedule.add(
            ScheduleResponse.Result(0, channelId, date, "21:00:00", "Need For speed")
        )
        return schedule
    }
//
//    fun generateRemoteDummyTvShow(): TvResponse {
//        val tvShows = ArrayList<TvResponse.Result>()
//        tvShows.add(
//            TvResponse.Result(
//            )
//        )
//        return TvResponse(tvShows, dummyTotalPage)
//    }
//
//    fun generateRemoteDummyDetailMovie(): MovieResponse.Result {
//        return MovieResponse.Result(
//        )
//    }
//
//    fun generateRemoteDummyDetailTvShow(): TvResponse.Result {
//        return TvResponse.Result(
//        )
//    }
//
//    fun generateDummyFavMovie(): List<MovieFavorite> {
//        val favMovie = ArrayList<MovieFavorite>()
//        favMovie.add(
//            MovieFavorite(
//                generateDummyMovies()[0],
//                FavoriteEntity(generateDummyMovies()[0].id)
//            )
//        )
//        favMovie.add(
//            MovieFavorite(
//                generateDummyMovies()[1],
//                FavoriteEntity(generateDummyMovies()[1].id)
//            )
//        )
//        return favMovie
//    }
//
//    fun generateDummyFavTv(): ArrayList<TvFavorite> {
//        val favTv = ArrayList<TvFavorite>()
//        favTv.add(
//            TvFavorite(
//                generateDummyTvShow()[0],
//                FavoriteEntity(generateDummyTvShow()[0].id)
//            )
//        )
//        favTv.add(
//            TvFavorite(
//                generateDummyTvShow()[1],
//                FavoriteEntity(generateDummyTvShow()[1].id)
//            )
//        )
//        return favTv
//    }
}