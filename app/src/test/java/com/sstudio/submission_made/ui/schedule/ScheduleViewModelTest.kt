package com.sstudio.submission_made.ui.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asFlow
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.domain.model.Schedule
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.core.utils.DataDummy
import com.sstudio.submission_made.core.utils.DataMapper
import com.sstudio.submission_made.ui.schedule.content.ScheduleContentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ScheduleViewModelTest {
    private lateinit var viewModel: ScheduleContentViewModel
    private val dummyChannel = DataDummy.generateDummyChannel()[0]
    private val channelId = dummyChannel.id
    private var simpleDate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).also {
        it.timeZone = TimeZone.getDefault()
    }
    private val date = simpleDate.format(Calendar.getInstance().time)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tvGuideUseCase: TvGuideUseCase

    @Mock
    private lateinit var observer: Observer<Resource<List<Schedule>>>

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ScheduleContentViewModel(tvGuideUseCase)
        viewModel.channelId = channelId
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetSchedule() {
        val dataDummy = DataDummy.generateDummySchedule(channelId, date)
        val dummyChannelWithSchedule = Resource.Success(DataMapper.mapScheduleEntitiesToDomain(dataDummy))
        val scheduleData = MutableLiveData<Resource<List<Schedule>>>()
        scheduleData.value = dummyChannelWithSchedule

        print(scheduleData.asFlow().toString())

        Mockito.`when`(tvGuideUseCase.getSchedule(false, channelId, date)).thenReturn(scheduleData.asFlow())
        viewModel.schedule.observeForever(observer)
        viewModel.positionDay.postValue(0)
        Thread.sleep(2000)
        val schedule = viewModel.schedule.value?.data
        Mockito.verify(tvGuideUseCase).getSchedule(false, channelId, date)
        Assert.assertNotNull(schedule)
        Assert.assertEquals(2, schedule?.size)
        Mockito.verify(observer).onChanged(dummyChannelWithSchedule)
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