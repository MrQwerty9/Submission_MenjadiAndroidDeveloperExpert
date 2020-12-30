package com.sstudio.submission_made.ui.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.vo.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ScheduleViewModelTest {
    private lateinit var viewModel: ScheduleViewModel
    private val dummyChannel = DataDummy.generateDummyChannel()[0]
    private val channelId = dummyChannel.id
    private val date = "2020-12-25"
    private val dummySchedule = DataDummy.generateDummySchedule(channelId, date)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvGuideRepository: TvGuideRepository

    @Mock
    private lateinit var observer: Observer<Resource<ChannelWithSchedule>>

    @Before
    fun setUp() {
        viewModel = ScheduleViewModel(tvGuideRepository)
        viewModel.channelId = channelId
        viewModel.date = date
    }

    @Test
    fun testGetSchedule() {
        val dummyChannelWithSchedule = Resource.success(DataDummy.generateDummyChannelWithSchedule(channelId, date))
        val course = MutableLiveData<Resource<ChannelWithSchedule>>()
        course.value = dummyChannelWithSchedule

        Mockito.`when`(tvGuideRepository.getSchedule(false, channelId, date)).thenReturn(course)
        val movieEntities = viewModel.schedule?.value?.data
        Mockito.verify(tvGuideRepository).getSchedule(false, channelId, date)
        Assert.assertNotNull(movieEntities)
        Assert.assertEquals(2, movieEntities?.scheduleEntity?.size)

        viewModel.schedule?.observeForever(observer)

        Mockito.verify(observer).onChanged(dummyChannelWithSchedule)
    }

    @Test
    fun testFetchSchedule() {}

    @Test
    fun testSetFavorite() {}

    @Test
    fun testGetFavoriteStatus() {}

    @Test
    fun testDeleteFavorite() {}
}