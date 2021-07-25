package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import models.TimeTableDay
import models.TimeTableDayAdapter
import models.User

class TimeTableActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val firestore = FirebaseFirestore.getInstance()
    private var usn: String? = null
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        val spinner: Spinner = findViewById(R.id.spinnerTimeTableDay)
        spinner.onItemSelectedListener = this

        usn = intent.getStringExtra("usn")
        println(usn)
        recyclerView = findViewById(R.id.rvTimeTable)


    }

    private fun createTimeTableDay(day: String): List<TimeTableDay> {
        var timeTableDayList = mutableListOf<TimeTableDay>()
        val userDocRef = firestore.collection("users").document(usn!!)
        val timetableCollectionRef = firestore.collection("timetable")
        userDocRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                user = it.result?.toObject(User::class.java)
                println(user)
                println(user?.branch)
                timetableCollectionRef.whereEqualTo("branch", user?.branch)
                    .whereEqualTo("sem", user?.sem).whereEqualTo("sec", user?.sec)
                    .whereEqualTo("day", day).get().addOnSuccessListener { result ->
                    timeTableDayList = result.toObjects(TimeTableDay::class.java)
                    recyclerView.adapter = TimeTableDayAdapter(this, timeTableDayList)
                    recyclerView.layoutManager = LinearLayoutManager(this)
//                    recyclerView.adapter?.notifyDataSetChanged()
                    for (timeTableDay in timeTableDayList) {
                        println("TimetableDay ${timeTableDay}")
                    }
                }
            } else {
                println("Error: $it")
            }
        }


//        val timeTableDayList = createTimeTableDay()
//        for(i in 1..100){
//            timeTableDayList.add(
//                TimeTableDay("18CS$i", "$i")
//            )
//        }
        return timeTableDayList
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val day = parent?.getItemAtPosition(position).toString()
        createTimeTableDay(day)
        println(day)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        println(parent)
    }
}