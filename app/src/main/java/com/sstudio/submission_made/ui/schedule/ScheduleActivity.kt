package com.sstudio.submission_made.ui.schedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submission_made.BuildConfig
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.domain.model.Channel
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.content_detail_schedule.*
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CHANNEL = "extra_channel"
        var channelId = 0
    }

    private var isFavorite: Boolean? = null
    private val viewModel: ScheduleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val extras = intent.getParcelableExtra<Channel>(EXTRA_CHANNEL)
        appbar.title = extras?.channel
        setSupportActionBar(appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f


        if (extras != null) {
            channelId = extras.id
            if (channelId > 0) {
                viewModel.getFavoriteStatus(channelId).observe(this, {
                    isFavorite = it?.channelId != null
                    favoriteOnChange()
                })
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
//            swipe_layout.setOnRefreshListener {
//                swipe_layout.isRefreshing = false
//                viewModel.fetchSchedule()
//                observeData()
//            }

            val adapter = SchedulePagerAdapter(supportFragmentManager)
            view_pager_schedule.adapter = adapter

            tab_layout_schedule.setupWithViewPager(view_pager_schedule)

            Glide.with(this)
                .load(BuildConfig.POSTER + extras.logoPath)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(img_backdrop)
        }
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
}