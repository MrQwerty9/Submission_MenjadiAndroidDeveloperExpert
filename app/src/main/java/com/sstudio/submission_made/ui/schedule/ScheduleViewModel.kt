package com.sstudio.submission_made.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.vo.Resource
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var channelId = 0
    private val simpleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var date: String = "2021-01-11" //simpleDate.format(Calendar.getInstance().time)
    var schedule: LiveData<Resource<List<ChannelWithScheduleModel>>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideUseCase.getSchedule(false, channelId, date).toLiveData()
            }
            return field
        }
        private set

    fun fetchSchedule(){
        schedule = tvGuideUseCase.getSchedule(true, channelId, date).toLiveData()
    }

    fun setFavorite(id: Int) =
        tvGuideUseCase.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<List<Favorite>> =
        tvGuideUseCase.getFavoriteById(id).toLiveData()

    fun deleteFavorite(id: Int){
        tvGuideUseCase.deleteFavorite(id)
    }
}