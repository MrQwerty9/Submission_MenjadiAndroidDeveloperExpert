package com.sstudio.submission_made.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.utils.LiveDataTestUtil
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
    private val movieTvRepository = FakeITvGuideRepository(remote, local, appExecutors)

    private val date = "2020-12-25"
    private val channelResponses = DataDummy.generateRemoteDummyChannel()
    private val channelId = channelResponses[0].id
    private val scheduleResponses = DataDummy.generateRemoteDummySchedule(channelId, date)

    @Test
    fun testGetAllChannel() {}

    @Test
    fun testGetChannel() {
        val dummyEntity = MutableLiveData<ChannelWithSchedule>()
        dummyEntity.value = DataDummy.generateDummyChannelWithSchedule(channelId, date)
        Mockito.`when`(local.getChannelWithScheduleById(channelId)).thenReturn(dummyEntity)

        val courseEntities = LiveDataTestUtil.getValue(movieTvRepository.getSchedule(false, channelId, date))
        verify(local).getChannelWithScheduleById(channelId)
        Assert.assertNotNull(courseEntities.data)
        Assert.assertNotNull(courseEntities.data?.channelEntity?.id)
        assertEquals(channelResponses[0].id, courseEntities.data?.channelEntity?.id)
    }

    @Test
    fun testGetSchedule() {
        val dummyEntity = MutableLiveData<ChannelWithSchedule>()
        dummyEntity.value = DataDummy.generateDummyChannelWithSchedule(channelId, date)
        Mockito.`when`(local.getChannelWithScheduleById(channelId)).thenReturn(dummyEntity)

        val scheduleEntities = LiveDataTestUtil.getValue(movieTvRepository.getSchedule(false, channelId, date))
        verify(local).getChannelWithScheduleById(channelId)
        Assert.assertNotNull(scheduleEntities.data)
        Assert.assertNotNull(scheduleEntities.data?.scheduleEntity?.get(0)?.id)
        assertEquals(scheduleResponses[0].showTimes[0].title, scheduleEntities.data?.scheduleEntity?.get(0)?.title)
    }

    fun testGetAllFavoriteChannel() {}

    fun testSetFavorite() {}

    fun testGetFavoriteById() {}

    fun testDeleteFavoriteTv() {}
}