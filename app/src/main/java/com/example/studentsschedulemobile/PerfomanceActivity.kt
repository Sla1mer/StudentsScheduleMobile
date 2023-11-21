package com.example.studentsschedulemobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PerfomanceActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfomance)

        listView = findViewById(R.id.listView3)

        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://studentssheduledb-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val uid = user.uid
            val usersNode = databaseReference.child("users").child(uid)

            usersNode.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.value as? Map<String, String>

                        if (userData != null) {
                            // Преобразуем данные для отображения в ListView
                            val dataList = mutableListOf<String>()
                            for ((subject, result) in userData) {
                                dataList.add("$subject - $result")
                            }

                            // Используем адаптер для отображения данных в ListView
                            val adapter = ArrayAdapter(
                                this@PerfomanceActivity,
                                android.R.layout.simple_list_item_1,
                                dataList
                            )

                            listView.adapter = adapter
                        }
                    } else {
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибок при чтении данных
                }
            })
        }
    }
}