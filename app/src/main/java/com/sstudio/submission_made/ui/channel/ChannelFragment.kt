package com.sstudio.submission_made.ui.channel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.core.ui.ChannelAdapter
import com.sstudio.submission_made.ui.schedule.ScheduleActivity
import kotlinx.android.synthetic.main.fragment_channel.*
import org.koin.android.viewmodel.ext.android.viewModel

class ChannelFragment : Fragment() {

    private lateinit var channelAdapter: ChannelAdapter
    private val viewModel: ChannelViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_channel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            toolbar.title = context?.getString(R.string.tab_channel)
            channelAdapter = ChannelAdapter()
            observeData()

            swipe_layout.setOnRefreshListener {
                viewModel.fetchListMovie()
                observeData()
                swipe_layout.isRefreshing = false
            }
            with(rv_list_favorite) {
                layoutManager = GridLayoutManager(context, 2)
//                setHasFixedSize(true)
            }
        }

        channelAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, ScheduleActivity::class.java)
            intent.putExtra(ScheduleActivity.EXTRA_CHANNEL, selectedData)
            startActivity(intent)
        }
    }

    private fun observeData() {
        viewModel.listChannel?.observe(viewLifecycleOwner, { listMovie ->
            if (listMovie != null) {
                when (listMovie) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        channelAdapter.submitList(listMovie.data)
                        rv_list_favorite.adapter = channelAdapter //why??
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}