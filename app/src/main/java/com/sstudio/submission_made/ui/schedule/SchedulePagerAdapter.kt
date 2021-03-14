package com.sstudio.submission_made.ui.schedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sstudio.submission_made.ui.schedule.content.ScheduleContentFragment

class SchedulePagerAdapter(fm: FragmentManager): FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val title = arrayOf("One", "Two", "Three")

    companion object {
       const val PAGE_COUNT = 3
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
}