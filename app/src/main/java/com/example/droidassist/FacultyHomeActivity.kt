package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import models.Faculty

class FacultyHomeActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore? = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_home)

        val facultyEmail: String = auth.currentUser?.email!!
        println(facultyEmail)
        val facultyName: TextView = findViewById(R.id.tvFacultyUsername)
        val facultyBranch: TextView = findViewById(R.id.tvFacultyDepartment)

        val userDocRef = db?.collection("faculty")?.document(facultyEmail)

        userDocRef?.get()
            ?.addOnSuccessListener { userDocSnapshot ->
                val faculty: Faculty? = userDocSnapshot.toObject(Faculty::class.java)
                facultyName.text = faculty?.name?.capitalize()
                facultyBranch.text = faculty?.branch
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }


    }


}