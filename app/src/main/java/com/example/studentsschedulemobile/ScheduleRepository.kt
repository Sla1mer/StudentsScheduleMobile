package com.example.studentsschedulemobile

import com.google.firebase.database.*
import java.io.Serializable

class ScheduleRepository {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://studentssheduledb-default-rtdb.europe-west1.firebasedatabase.app/").reference

    // Метод для чтения расписания
    fun readSchedule(callback: (List<List<ScheduleDay>>) -> Unit) {
        val scheduleRef = databaseReference.child("schedule")

        scheduleRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val scheduleData = mutableListOf<List<ScheduleDay>>()

                for (daySnapshot in dataSnapshot.children) {
                    // Изменяем: получаем список объектов ScheduleDay, а не один объект
                    val daySchedule = daySnapshot.getValue(object : GenericTypeIndicator<List<ScheduleDay>>() {})
                    daySchedule?.let {
                        scheduleData.add(it)
                    }
                }

                // Вызываем колбэк с полученными данными
                callback(scheduleData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибки чтения данных
            }
        })
    }
}


data class ScheduleDay(
    val auditory: String? = null,
    val date: String? = null,
    val fio: String? = null,
    val number: String? = null,
    val subject: String? = null
) : Serializable

