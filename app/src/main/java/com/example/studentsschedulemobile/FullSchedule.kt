package com.example.studentsschedulemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.content.ContentProviderCompat.requireContext

class FullSchedule : AppCompatActivity() {
    private lateinit var adapter: DayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_schedule)


        val schedule = intent.getSerializableExtra("schedule") as? Array<ScheduleDay>

        val listView: ListView = findViewById(R.id.listView2)

        val adapter = schedule?.let {
            CustomArrayAdapter(
                this,
                R.layout.custom_list_item,
                it.toList()
            )
        }

        listView.adapter = adapter
    }
}