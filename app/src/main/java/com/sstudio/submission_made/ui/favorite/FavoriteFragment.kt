package com.sstudio.submission_made.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.ui.ChannelAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var channelAdapter: ChannelAdapter
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            channelAdapter = ChannelAdapter()
            viewModel.listChannel?.observe(this, { listFavorite ->
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
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
//                adapter = movieAdapter
            }
        }
    }
}