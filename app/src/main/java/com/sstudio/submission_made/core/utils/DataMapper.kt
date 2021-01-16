package com.sstudio.submission_made.core.utils

import androidx.paging.DataSource
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.ChannelFavorite
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import com.sstudio.submission_made.core.data.source.remote.response.ChannelResponse
import com.sstudio.submission_made.core.domain.model.Channel
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Schedule

object DataMapper {

    fun mapChanelResponsesToEntities(input: List<ChannelResponse.Result>): List<ChannelEntity> {
        val channelList = ArrayList<ChannelEntity>()
        input.map {
            val tourism = ChannelEntity(
                id = it.id,
                channel = it.channel,
                logoPath = it.logoPath
            )
            channelList.add(tourism)
        }
        return channelList
    }

    fun mapChannelEntitiesToDomain(input: DataSource.Factory<Int, ChannelEntity>): DataSource.Factory<Int, Channel> =
        input.map {
            Channel(
                id = it.id,
                channel = it.channel,
                logoPath = it.logoPath
            )
        }

    fun mapChannelScheduleEntitiesToDomain(input: ChannelWithSchedule): ChannelWithScheduleModel {
        return ChannelWithScheduleModel(
            channel = Channel(
                input.channelEntity.id,
                input.channelEntity.channel,
                input.channelEntity.logoPath
            ),
            schedule = input.scheduleEntity.map {
                Schedule(
                    channelId = it.channelId,
                    date = it.date,
                    time = it.time,
                    title = it.title
                )
            }
        )
    }
    fun mapChannelFavoriteToDomainPagedList(input: DataSource.Factory<Int, ChannelFavorite>): DataSource.Factory<Int, Channel> =
        input.map{
            Channel(
                id = it.channelEntity.id,
                channel = it.channelEntity.channel,
                logoPath = it.channelEntity.logoPath
            )
        }

    fun mapChannelDomainToEntity(input: Channel) = ChannelEntity(
        id = input.id,
        channel = input.channel,
        logoPath = input.logoPath
    )

    fun mapScheduleEntitiesToDomain(input: List<ScheduleEntity>): List<Schedule> =
        input.map {
            Schedule(
                channelId = it.channelId,
                date = it.date,
                time = it.time,
                title = it.title
            )
        }

    fun mapScheduleDomainToEntity(input: Schedule) = ScheduleEntity(
        channelId = input.channelId,
        date = input.date,
        time = input.time,
        title = input.title
    )
}