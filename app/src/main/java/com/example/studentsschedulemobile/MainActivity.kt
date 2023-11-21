package com.example.studentsschedulemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import java.time.LocalDate

class MainActivity : FragmentActivity() {

    private lateinit var adapter: DayAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var fullBtn: Button
    private lateinit var currentDay: Button
    private lateinit var perfomanceBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val scheduleRepository = ScheduleRepository()

        adapter = DayAdapter(this, scheduleRepository)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter

        fullBtn = findViewById(R.id.fullDayShceduleBtn)

        var listSchedule = emptyList<List<ScheduleDay>>()

        scheduleRepository.readSchedule { scheduleData ->
            listSchedule = scheduleData
        }

        fullBtn.setOnClickListener {
            val currentPosition = viewPager.currentItem

            val intent = Intent(this, FullSchedule::class.java)
            intent.putExtra("schedule", listSchedule[currentPosition].toTypedArray())
            startActivity(intent)
        }

        currentDay = findViewById(R.id.currentDay)

        currentDay.setOnClickListener {
            val currentDate = LocalDate.now()
            println(currentDate)
            val outerIndex: Int = listSchedule.indexOfFirst { sublist ->
                sublist.any { it.date == currentDate.toString() }
            }

            viewPager.setCurrentItem(outerIndex, true)
        }


        perfomanceBtn = findViewById(R.id.performanceBtn)

        perfomanceBtn.setOnClickListener {
            val intent = Intent(this, PerfomanceActivity::class.java)
            startActivity(intent)
        }
    }

}