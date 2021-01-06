package com.sstudio.submission_made.ui.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.core.utils.DataMapper
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
    private val date = "2021-01-05"
    private val dummySchedule = DataDummy.generateDummySchedule(channelId, date)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvGuideUseCase: TvGuideUseCase

    @Mock
    private lateinit var observer: Observer<Resource<ChannelWithScheduleModel>>

    @Before
    fun setUp() {
        viewModel = ScheduleViewModel(tvGuideUseCase)
        viewModel.channelId = channelId
        viewModel.date = date
    }

    @Test
    fun testGetSchedule() {
        val dataDummy = DataDummy.generateDummyChannelWithSchedule(channelId, date)
        val dummyChannelWithSchedule = Resource.Success(DataMapper.mapChannelScheduleEntitiesToDomain(dataDummy))
        val course = MutableLiveData<Resource<ChannelWithScheduleModel>>()
        course.value = dummyChannelWithSchedule
        print(course.asFlow().toString())

        Mockito.`when`(tvGuideUseCase.getSchedule(false, channelId, date)).thenReturn(course.asFlow())
        val schedule = viewModel.schedule?.value?.data
        Mockito.verify(tvGuideUseCase).getSchedule(false, channelId, date)
//        Assert.assertNotNull(schedule)
//        Assert.assertEquals(2, schedule?.schedule?.size)

//        viewModel.schedule?.observeForever(observer)

//        Mockito.verify(observer).onChanged(dummyChannelWithSchedule)
    }

    @Test
    fun testFetchSchedule() {
    }

    @Test
    fun testSetFavorite() {}

    @Test
    fun testGetFavoriteStatus() {}

    @Test
    fun testDeleteFavorite() {}
}