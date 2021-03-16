package com.sstudio.submission_made.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.sstudio.submission_made.core.data.source.local.LocalDataSource
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import com.sstudio.submission_made.core.data.source.remote.RemoteDataSource
import com.sstudio.submission_made.core.utils.AppExecutors
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.core.utils.LiveDataTestUtil
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvGuideRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val tvGuideRepository =
        FakeTvGuideRepository(
            remote,
            local,
            appExecutors
        )

    private val date = "2020-12-25"
    private val channelResponses = DataDummy.generateRemoteDummyChannel()
    private val channelId = channelResponses[0].id
    private val scheduleResponses = DataDummy.generateRemoteDummySchedule(channelId, date)
//    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

//    @Before
//    fun setUp() {
//        Dispatchers.setMain(mainThreadSurrogate)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
//    }

    @Test
    fun testGetAllChannel() {
        CoroutineScope(Dispatchers.IO).launch {
            val dummyEntity = MutableLiveData<List<ChannelEntity>>()
            val dataSourceFactoryDomain =
                Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ChannelEntity>
            dummyEntity.value = DataDummy.generateDummyChannel()
            Mockito.`when`(local.getAllChannels()).thenReturn(dataSourceFactoryDomain)

            tvGuideRepository.getAllChannel(false)

//            Thread.sleep(3000)
            val allChannelData =
                LiveDataTestUtil.getValue(tvGuideRepository.getAllChannel(false).asLiveData())
            verify(local).getAllChannels()
//        Assert.assertNotNull(allChannelData.data)
//        Assert.assertNotNull(courseEntities.data?)
//        assertEquals(channelResponses[0].id, allChannelData.data?.id)
        }
    }

    @Test
    fun testGetSchedule() {
        CoroutineScope(Dispatchers.IO).launch {
            val dummyEntity = MutableLiveData<List<ScheduleEntity>>()
            dummyEntity.value = DataDummy.generateDummySchedule(channelId, date)
            Mockito.`when`(local.getSchedule(channelId, date))
                .thenReturn(dummyEntity.asFlow())

            val scheduleEntities = LiveDataTestUtil.getValue(
                tvGuideRepository.getSchedule(false, channelId, date).asLiveData()
            )
            verify(local).getSchedule(channelId, date)
            Assert.assertNotNull(scheduleEntities.data)
            Assert.assertNotNull(scheduleEntities.data?.get(0)?.channelId)
            assertEquals(scheduleResponses[0].title, scheduleEntities.data?.get(0)?.title)
        }
    }

    fun testGetAllFavoriteChannel() {}

    fun testSetFavorite() {}

    fun testGetFavoriteById() {}

    fun testDeleteFavoriteTv() {}
}