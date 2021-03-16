package com.sstudio.submission_made.ui.schedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sstudio.submission_made.ui.schedule.content.ScheduleContentFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SchedulePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val title = getDateName()

    companion object {
       const val PAGE_COUNT = 7
    }

    override fun getItem(position: Int): Fragment {
        return ScheduleContentFragment.getInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    private fun getDateName(): ArrayList<String>{
        val dateString = ArrayList<String>()
        val cal: Calendar = Calendar.getInstance()
        repeat(PAGE_COUNT){
            dateString.add(SimpleDateFormat("dd/MM", Locale("id", "ID")).format(cal.time))
            cal.add(Calendar.DATE, 1)
        }
        return dateString
    }
}