package com.sstudio.submission_made.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sstudio.submission_made.core.ui.ChannelAdapter
import com.sstudio.submission_made.favorite.di.viewModelModule
import com.sstudio.submission_made.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var channelAdapter: ChannelAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
//            toolbar.title = context?.getString(R.string.tab_favorite)
                loadKoinModules(viewModelModule)
            channelAdapter = ChannelAdapter()
            viewModel.listChannel?.observe(viewLifecycleOwner, { listFavorite ->
                if (listFavorite != null) {
                    channelAdapter.submitList(listFavorite)
                    rv_list_favorite.adapter = channelAdapter //why??
                    progress_bar.visibility = View.GONE
                }
            })

            swipe_layout.setOnRefreshListener {
                swipe_layout.isRefreshing = false
            }
            with(rv_list_favorite) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
            }
            channelAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, ScheduleActivity::class.java)
                intent.putExtra(ScheduleActivity.EXTRA_SCHEDULE, selectedData)
                startActivity(intent)
            }
        }
    }
}