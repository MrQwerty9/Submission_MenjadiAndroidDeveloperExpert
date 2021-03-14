package com.sstudio.submission_made.ui.schedule.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import java.text.SimpleDateFormat
import java.util.*

class ScheduleContentViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var positionDay = 0
    var channelId = 0
    private var simpleDate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).also {
        it.timeZone = TimeZone.getDefault()
    }
    private val cal: Calendar = Calendar.getInstance().also {
        it.add(Calendar.DATE, positionDay)
    }
    var date: String = simpleDate.format(cal.time)

//    var date = "2021-03-12"

    var schedule: LiveData<Resource<ChannelWithScheduleModel>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideUseCase.getSchedule(false, channelId, date).asLiveData()
            }
            return field
        }
        private set

    fun fetchSchedule(){
        schedule = tvGuideUseCase.getSchedule(true, channelId, date).asLiveData()
    }
}