package com.sstudio.submission_made.ui.schedule.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submission_made.R
import com.sstudio.submission_made.core.data.Resource
import com.sstudio.submission_made.ui.schedule.ScheduleActivity
import com.sstudio.submission_made.ui.schedule.ScheduleAdapter
import kotlinx.android.synthetic.main.fragment_schedule_content.*
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleContentFragment : Fragment() {

    private var position: Int? = null
    private var scheduleAdapter = ScheduleAdapter()
    private val viewModel: ScheduleContentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(BUNDLE_POS)

        viewModel.channelId = ScheduleActivity.channelId
        viewModel.positionDay = position ?: 0
        observeData()

        with(rv_cast) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = scheduleAdapter
        }

        tv_schedule_date.text = position.toString()
    }

    private fun observeData(){
        viewModel.schedule?.observe(viewLifecycleOwner, { schedule ->
            when (schedule) {
                is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                is Resource.Success -> {
                    progress_bar.visibility = View.GONE
                    schedule.data?.let {
                        it.schedule?.let { it1 -> scheduleAdapter.setListSchedule(it1) }
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {

        const val BUNDLE_POS = "pos"

        fun getInstance(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(BUNDLE_POS, position)
            val tabFragment = ScheduleContentFragment()
            tabFragment.arguments = bundle
            return tabFragment
        }
    }

}