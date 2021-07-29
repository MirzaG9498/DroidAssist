package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import models.*

class AttendanceActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance()
    private var usn: String? = null
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        usn = intent.getStringExtra("usn")
        println(usn)
        createAttendance()
        recyclerView = findViewById(R.id.rvAttendance)
    }

    private fun createAttendance(): List<Attendance> {

        var attendanceList = mutableListOf<Attendance>()
        val userDocRef = firestore.collection("users").document(usn!!)
        val attendanceCollectionRef = firestore.collection("attendance")

        userDocRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                user = it.result?.toObject(User::class.java)
                println(user)
                println(user?.branch)
                attendanceCollectionRef.whereEqualTo("branch", user?.branch)
                    .whereEqualTo("sem", user?.sem).whereEqualTo("sec", user?.sec)
                    .whereEqualTo("usn", user?.usn)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null || snapshot == null) {
                            Toast.makeText(
                                this,
                                "Empty List Or Error: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            attendanceList = snapshot.toObjects(Attendance::class.java)
                            attendanceList.sortBy { ia ->
                                ia.subjectCode
                            }
                            recyclerView.adapter = AttendanceAdapter(this, attendanceList)
                            recyclerView.layoutManager = LinearLayoutManager(this)
                            // recyclerView.adapter?.notifyDataSetChanged()
                            for (attendance in attendanceList) {
                                println("attendance $attendance")
                            }
                        }
                    }
            }
        }

//        for (i in 1..100) {
//            attendanceList.add(
//                Attendance(i.toDouble(),"18CS$i",i,100)
//            )
//        }
        return attendanceList
    }
}