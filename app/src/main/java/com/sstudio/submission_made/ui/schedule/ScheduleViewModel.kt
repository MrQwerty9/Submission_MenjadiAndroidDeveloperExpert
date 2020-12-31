package com.sstudio.submission_made.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sstudio.submission_made.core.domain.model.ChannelWithScheduleModel
import com.sstudio.submission_made.core.domain.model.Favorite
import com.sstudio.submission_made.core.domain.usecase.TvGuideUseCase
import com.sstudio.submission_made.vo.Resource
import java.text.SimpleDateFormat
import java.util.*

class ScheduleViewModel(private val tvGuideUseCase: TvGuideUseCase) : ViewModel() {

    var channelId = 0
    private val simpleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var date: String = simpleDate.format(Calendar.getInstance().time)
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

    fun setFavorite(id: Int) =
        tvGuideUseCase.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<Favorite> =
        tvGuideUseCase.getFavoriteById(id).asLiveData()

    fun deleteFavorite(id: Int){
        tvGuideUseCase.deleteFavorite(id)
    }
}