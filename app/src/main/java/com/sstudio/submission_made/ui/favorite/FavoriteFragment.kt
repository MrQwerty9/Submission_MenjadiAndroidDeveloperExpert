package com.sstudio.submission_made.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submission_made.MyApplication
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.ui.ViewModelFactory
import com.sstudio.submission_made.ui.channel.ChannelAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private lateinit var channelAdapter: ChannelAdapter
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
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
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
//                adapter = movieAdapter
            }
        }
    }
}