package com.sstudio.submission_made.ui.schedule.content

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import java.text.SimpleDateFormat
import java.util.*

class ScheduleContentViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var positionDay = MutableLiveData<Int>()
    var channelId = 0
    private var simpleDate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID")).also {
        it.timeZone = TimeZone.getDefault()
    }

//    var date = "2021-03-15"


    var schedule = Transformations.switchMap(positionDay){ positionDay ->
        val cal: Calendar = Calendar.getInstance().also {
            it.add(Calendar.DATE, positionDay)
        }
        val date: String = simpleDate.format(cal.time)

        tvGuideUseCase.getSchedule(false, channelId, date).asLiveData()
    }
//
//    fun fetchSchedule(){
//        schedule = tvGuideUseCase.getSchedule(true, channelId, date).asLiveData()
//    }
}