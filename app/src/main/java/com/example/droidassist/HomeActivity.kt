package com.example.droidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import models.User


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

        val userDocRef = database?.collection("users")?.document(usnIntent!!)
        userDocRef?.get()
            ?.addOnSuccessListener { userDocSnapshot ->
                user = userDocSnapshot.toObject(User::class.java)
                username.text = user?.username?.capitalize()
                usn.text = user?.usn
                department.text = user?.branch
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }

        val goToAttendance: LinearLayout = findViewById(R.id.goToAttendanceLinearLayout)
        goToAttendance.setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
        }

        val goToResources: LinearLayout = findViewById(R.id.goToResourceLinearLayout)
        goToResources.setOnClickListener {
            val intent = Intent(this, ResourcesActivity::class.java)
            startActivity(intent)
        }

        val goToTimeTable: TextView = findViewById(R.id.goToTimetable)
        goToTimeTable.setOnClickListener {
            val intent = Intent(this, TimeTableActivity::class.java)
            intent.putExtra("usn", user?.usn)
            startActivity(intent)
        }


    }

}
