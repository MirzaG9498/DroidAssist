package com.example.droidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import models.TimeTableDay
import models.User
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseFirestore? = Firebase.firestore
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userEmail: String = auth.currentUser?.email!!
        val usnIntent: String? = intent.getStringExtra("userUsn")
        val username: TextView = findViewById(R.id.tvHomeUsername)
        val department: TextView = findViewById(R.id.tvHomeUserDepartment)
        val usn: TextView = findViewById(R.id.tvHomeUserUsn)
        val onGoingClassCode: TextView = findViewById(R.id.ongoingClassName)
        val day = getCurrentDay()
        val timeFormat = SimpleDateFormat("hh:mm")
        val currentTime = timeFormat.format(Date())
        var timeTableDayList = mutableListOf<TimeTableDay>()
        val userDocRef = database?.collection("users")?.document(usnIntent!!)
        val timetableCollectionRef = database?.collection("timetable")
        var currentSubjects = ""
        userDocRef?.get()
            ?.addOnSuccessListener { userDocSnapshot ->
                user = userDocSnapshot.toObject(User::class.java)
                timetableCollectionRef?.whereEqualTo("branch", user?.branch)
                    ?.whereEqualTo("sem", user?.sem)?.whereEqualTo("sec", user?.sec)
                    ?.whereEqualTo("day", day)
                    ?.get()?.addOnSuccessListener { result ->
                        timeTableDayList = result.toObjects(TimeTableDay::class.java)
                        timeTableDayList.sortedBy { timeTableDay ->
                            timeTableDay.subjectCode
                        }
                        for (timeTableDay in timeTableDayList) {
                            if (currentTime <= timeTableDay.slot) {
                                println(currentTime)
                                println(timeTableDay.slot)
                            }
                            currentSubjects += timeTableDay.subjectCode + "\n"
                            println(currentSubjects)

                            println(timeTableDay)
                        }
                        onGoingClassCode.text = currentSubjects
                    }
                    ?.addOnFailureListener {
                        println(it)
                    }

                username.text = user?.username?.capitalize()
                usn.text = user?.usn
                department.text = user?.branch
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }

        val goToAttendance: LinearLayout = findViewById(R.id.goToAttendanceLinearLayout)
        goToAttendance.setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            intent.putExtra("usn", user?.usn)
            startActivity(intent)
        }

        val goToResources: LinearLayout = findViewById(R.id.goToResourceLinearLayout)
        goToResources.setOnClickListener {
            val intent = Intent(this, ResourcesActivity::class.java)
            intent.putExtra("usn", user?.usn)
            startActivity(intent)
        }

        val goToTimeTable: TextView = findViewById(R.id.goToTimetable)
        goToTimeTable.setOnClickListener {
            val intent = Intent(this, TimeTableActivity::class.java)
            intent.putExtra("usn", user?.usn)
            startActivity(intent)
        }

        val goToIA: LinearLayout = findViewById(R.id.goToIALinearLayout)
        goToIA.setOnClickListener {
            val intent = Intent(this, InternalAssessmentActivity::class.java)
            intent.putExtra("usn", user?.usn)
            startActivity(intent)
        }

    }

    private fun getCurrentDay(): String {
        val calendar: Calendar = Calendar.getInstance()
        val day = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            Calendar.SUNDAY -> "Sunday"
            else -> "Invalid Day"
        }
        return day
    }

}
