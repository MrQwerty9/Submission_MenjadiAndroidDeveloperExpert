package com.sstudio.submission_made.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sstudio.submission_made.vo.Resource
import com.sstudio.submission_made.core.data.TvGuideRepository
import com.sstudio.submission_made.core.data.source.local.entity.ChannelWithSchedule
import com.sstudio.submission_made.core.data.source.local.entity.FavoriteEntity
import java.text.SimpleDateFormat
import java.util.*

class ScheduleViewModel(private val tvGuideRepository: TvGuideRepository) : ViewModel() {

    var channelId = 0
    private val simpleDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var date: String = simpleDate.format(Calendar.getInstance().time)
    var schedule: LiveData<Resource<ChannelWithSchedule>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
                field = tvGuideRepository.getSchedule(false, channelId, date)
            }
            return field
        }
        private set

    fun fetchSchedule(){
        schedule = tvGuideRepository.getSchedule(true, channelId, date)
    }

    fun setFavorite(id: Int) =
        tvGuideRepository.setFavorite(id)

    fun getFavoriteStatus(id: Int): LiveData<List<FavoriteEntity>> =
        tvGuideRepository.getFavoriteById(id)

    fun deleteFavorite(id: Int){
        tvGuideRepository.deleteFavoriteTv(id)
    }
}