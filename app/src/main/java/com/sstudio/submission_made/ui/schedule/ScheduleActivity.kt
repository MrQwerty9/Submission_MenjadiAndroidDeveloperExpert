package com.sstudio.submission_made.ui.schedule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submission_made.BuildConfig
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.domain.model.Channel
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.content_detail_schedule.*
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SCHEDULE = "extra_schedule"
    }

    var isFavorite: Boolean? = null
    private lateinit var scheduleAdapter: ScheduleAdapter
    private val viewModel: ScheduleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val extras = intent.getParcelableExtra<Channel>(EXTRA_SCHEDULE)
        appbar.title = extras?.channel
        setSupportActionBar(appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f
        scheduleAdapter = ScheduleAdapter()

        if (extras != null) {
            val channelId = extras.id
            if (channelId != 0) {
                viewModel.getFavoriteStatus(channelId).observe(this, {
                    isFavorite = it?.channelId != null
                    favoriteOnChange()
                })
                viewModel.channelId = channelId
                observeData()
            }
            with(rv_cast) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = scheduleAdapter
            }
            btn_favorite.setOnClickListener {
                isFavorite?.let { isFavorite ->
                    if (isFavorite) {
                        viewModel.deleteFavorite(channelId)
                    } else {
                        viewModel.setFavorite(channelId)
                    }
                    favoriteOnChange()
                }
            }
            swipe_layout.setOnRefreshListener {
                swipe_layout.isRefreshing = false
                viewModel.fetchSchedule()
                observeData()
            }
        }
    }

    private fun observeData(){
        viewModel.schedule?.observe(this, { schedule ->
            when (schedule) {
                is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                is Resource.Success -> {
                    progress_bar.visibility = View.GONE
                    schedule.data?.let {
                        it.channel?.let { it1 -> populateMovie(it1) }
                        it.schedule?.let { it1 -> scheduleAdapter.setListSchedule(it1) }
//                        Log.d("mytag", "sche activ ${it}")
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun favoriteOnChange() {
        isFavorite?.let { isFavorite ->
            if (!isFavorite) {
                btn_favorite.setImageResource(R.drawable.ic_favorite_border_white)
            } else {
                btn_favorite.setImageResource(R.drawable.ic_favorite_pink)
            }
        }
    }

    private fun populateMovie(schedule: Channel) {
//        appbar.title = schedule.channelEntity.channel
        Glide.with(this)
            .load(BuildConfig.POSTER + schedule.logoPath)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
            )
            .into(img_backdrop)
    }
}