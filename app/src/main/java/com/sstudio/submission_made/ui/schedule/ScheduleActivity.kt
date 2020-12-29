package com.sstudio.submission_made.ui.schedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submission_made.BuildConfig
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.source.local.entity.ChannelEntity
import com.sstudio.submission_made.core.ui.ViewModelFactory
import com.sstudio.submission_made.vo.Status
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.content_detail_schedule.*

class ScheduleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SCHEDULE = "extra_schedule"
    }

    var isFavorite: Boolean? = null
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var viewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        supportActionBar?.elevation = 0f

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ScheduleViewModel::class.java]
        scheduleAdapter = ScheduleAdapter()
        val extras = intent.getParcelableExtra<ChannelEntity>(EXTRA_SCHEDULE)
        if (extras != null) {
            val channelId = extras.id
            if (channelId != 0) {
                viewModel.getFavoriteStatus(channelId).observe(this, {
                    isFavorite = it.isNotEmpty()
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
            when (schedule.status) {
                Status.LOADING -> progress_bar.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    schedule.data?.let {
                        populateMovie(it.channelEntity)
                        scheduleAdapter.setListSchedule(it.scheduleEntity)
//                        Log.d("mytag", "sche activ ${it}")
                    }
                }
                Status.ERROR -> {
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

    private fun populateMovie(schedule: ChannelEntity) {
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