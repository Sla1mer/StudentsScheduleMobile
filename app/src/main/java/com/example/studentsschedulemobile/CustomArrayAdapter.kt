package com.example.studentsschedulemobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomArrayAdapter(context: Context, resource: Int, objects: List<ScheduleDay>) :
    ArrayAdapter<ScheduleDay>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val itemView = convertView ?: inflater.inflate(R.layout.custom_list_item, parent, false)

        val scheduleDay = getItem(position)
        val textView = itemView.findViewById<TextView>(R.id.customTextView)

        println(scheduleDay)
        textView.text = "${scheduleDay?.number}. " +
                "${scheduleDay?.subject} - ${scheduleDay?.auditory}\n" +
                "${scheduleDay?.fio}"

        return itemView
    }
}

