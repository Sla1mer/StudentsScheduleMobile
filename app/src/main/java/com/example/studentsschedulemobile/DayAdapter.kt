package com.example.studentsschedulemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DayAdapter(fragment: FragmentActivity, private val scheduleRepository: ScheduleRepository) : FragmentStateAdapter(fragment) {
    private var daysOfWeek = emptyList<List<ScheduleDay>>()

    init {
        scheduleRepository.readSchedule { scheduleData ->
            daysOfWeek = scheduleData
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = daysOfWeek.size

    override fun createFragment(position: Int): Fragment {

        return DateFragment.newInstance(daysOfWeek[position])
    }

}