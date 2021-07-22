package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Attendance
import models.AttendanceAdapter

class AttendanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        val attendanceList = createAttendance()
        val recyclerView: RecyclerView = findViewById(R.id.rvAttendance)
        recyclerView.adapter = AttendanceAdapter(this,attendanceList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun createAttendance(): List<Attendance> {
        val attendanceList = mutableListOf<Attendance>()
        for (i in 1..100) {
            attendanceList.add(
                Attendance(i.toDouble(),"18CS$i",i,100)
            )
        }
        return attendanceList
    }
}