package com.sstudio.submission_made.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sstudio.submission_made.BuildConfig
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.source.local.entity.ChannelFavorite
import kotlinx.android.synthetic.main.item_channel.view.*

class FavoriteAdapter : PagedListAdapter<ChannelFavorite, FavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((ChannelFavorite) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChannelFavorite>() {
            override fun areItemsTheSame(oldItem: ChannelFavorite, newItem: ChannelFavorite): Boolean {
                return oldItem.channelEntity.id == newItem.channelEntity.id
            }

            override fun areContentsTheSame(oldItem: ChannelFavorite, newItem: ChannelFavorite): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(channel: ChannelFavorite) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER + channel.channelEntity.logoPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(img_poster)
                itemView.setOnClickListener {
                    onItemClick?.invoke(channel)
                }
            }
        }
    }
}