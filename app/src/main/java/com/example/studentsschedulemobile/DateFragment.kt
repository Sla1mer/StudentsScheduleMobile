package com.example.studentsschedulemobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.io.Serializable
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

const val ARG_OBJECT = "object"

class DateFragment : Fragment() {

    private lateinit var currentDate: LocalDate // Используйте Java 8 API для работы с датами
    lateinit var listSchedule: List<ScheduleDay>


    companion object {
        private const val ARG_DATE = "date"
        private const val ARG_LIST = "dateList"

        fun newInstance(date: List<ScheduleDay>): DateFragment {
            val fragment = DateFragment()
            val args = Bundle()
            args.putString(ARG_DATE, date[1].date)
            args.putSerializable(ARG_LIST, ArrayList(date) as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_date, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dateText: TextView = view.findViewById(R.id.textView)
        val dateText2: TextView = view.findViewById(R.id.textView2)

        val listView: ListView = view.findViewById(R.id.listView)

        dateText.text = currentDate.toString()

        val russianDayOfWeek = getRussianDayOfWeek(currentDate)
        dateText2.text = russianDayOfWeek

        // Создаем адаптер для ListView
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, // Простой макет для каждого элемента списка
            listSchedule.map { "${it.subject}" } // Пример: отображаем предмет и аудиторию
        )

        // Устанавливаем адаптер для ListView
        listView.adapter = adapter

        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val deltaX = event.x - event.downTime
                    if (deltaX > 0) {
                        currentDate = currentDate.minusDays(1)
                    } else {
                        currentDate = currentDate.plusDays(1)
                    }
                    dateText.text = currentDate.toString()

                    // Обновляем данные в ListView при смене даты
                    adapter.clear()
                    adapter.addAll(listSchedule.map { "${it.subject} - ${it.auditory}" })
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dateArgument = arguments?.getString(ARG_DATE)
        currentDate = LocalDate.parse(dateArgument)
        val scheduleArgument = arguments?.getSerializable(ARG_LIST)
        listSchedule = (scheduleArgument as? List<ScheduleDay>) ?: emptyList()
    }

    fun getRussianDayOfWeek(localDate: LocalDate): String {
        val dayOfWeek = localDate.dayOfWeek
        val russianLocale = Locale("ru")
        return dayOfWeek.getDisplayName(TextStyle.FULL_STANDALONE, russianLocale)
    }
}
