package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.TimeTableDay
import models.TimeTableDayAdapter

class TimeTableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        val spinner: Spinner = findViewById(R.id.spinnerTimeTableDay)

        val timeTableDayList = createTimeTableDay()
        val recyclerView:RecyclerView = findViewById(R.id.rvTimeTable)
        recyclerView.adapter = TimeTableDayAdapter(this, timeTableDayList)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun createTimeTableDay(): List<TimeTableDay> {
        val timeTableDayList = mutableListOf<TimeTableDay>()
        for(i in 1..100){
            timeTableDayList.add(
                TimeTableDay("18CS$i", "$i")
            )
        }
        return timeTableDayList
    }
}