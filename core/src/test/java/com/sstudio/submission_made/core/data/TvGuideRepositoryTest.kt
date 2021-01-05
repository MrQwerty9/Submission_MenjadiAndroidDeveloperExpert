package com.sstudio.submission_made.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.core.utils.LiveDataTestUtil
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class TvGuideRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val movieTvRepository =
        FakeTvGuideRepository(
            remote,
            local,
            appExecutors
        )

    private val date = "2020-12-25"
    private val channelResponses = DataDummy.generateRemoteDummyChannel()
    private val channelId = channelResponses[0].id
    private val scheduleResponses = DataDummy.generateRemoteDummySchedule(channelId, date)

    @Test
    fun testGetAllChannel() {}

    @Test
    fun testGetChannel() {
//        val dummyEntity = MutableLiveData<ChannelWithSchedule>()
//        dummyEntity.value = DataDummy.generateDummyChannelWithSchedule(channelId, date)
//        Mockito.`when`(local.getChannelWithScheduleById(channelId, date)).thenReturn(dummyEntity.asFlow())
//
//        val courseEntities = LiveDataTestUtil.getValue(movieTvRepository.getSchedule(false, channelId, date).asLiveData())
//        verify(local).getChannelWithScheduleById(channelId, date)
//        Assert.assertNotNull(courseEntities.data)
//        Assert.assertNotNull(courseEntities.data?.channel?.id)
//        assertEquals(channelResponses[0].id, courseEntities.data?.channel?.id)
    }

    @Test
    fun testGetSchedule() {
//        val dummyEntity = MutableLiveData<ChannelWithSchedule>()
//        dummyEntity.value = DataDummy.generateDummyChannelWithSchedule(channelId, date)
//        Mockito.`when`(local.getChannelWithScheduleById(channelId, date)).thenReturn(dummyEntity.asFlow())
//
//        val scheduleEntities = LiveDataTestUtil.getValue(movieTvRepository.getSchedule(false, channelId, date).asLiveData())
//        verify(local).getChannelWithScheduleById(channelId, date)
//        Assert.assertNotNull(scheduleEntities.data)
//        Assert.assertNotNull(scheduleEntities.data?.schedule?.get(0)?.channelId)
//        assertEquals(scheduleResponses[0].showTimes[0].title, scheduleEntities.data?.schedule?.get(0)?.title)
    }

    fun testGetAllFavoriteChannel() {}

    fun testSetFavorite() {}

    fun testGetFavoriteById() {}

    fun testDeleteFavoriteTv() {}
}