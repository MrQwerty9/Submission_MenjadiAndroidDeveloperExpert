package com.sstudio.submission_made.ui.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.source.local.entity.ScheduleEntity
import kotlinx.android.synthetic.main.item_schedule.view.*
import java.util.ArrayList

class ScheduleAdapter() : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private val listSchedule = ArrayList<ScheduleEntity>()

    fun setListSchedule(schedule: List<ScheduleEntity>){
        this.listSchedule.clear()
        this.listSchedule.addAll(schedule)
        notifyDataSetChanged()
        Log.d("mytag", "adapter $schedule")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listSchedule.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listSchedule[position]
        holder.bind(movie)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(channel: ScheduleEntity) {
            with(itemView) {
                tv_item_title.text = channel.title
                tv_item_time.text = channel.time
            }
        }
    }

}