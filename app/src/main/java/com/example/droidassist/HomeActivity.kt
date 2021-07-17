package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userEmail: String = auth.currentUser?.email!!
        val username: TextView = findViewById(R.id.tvHomeUsername)
        val department: TextView = findViewById(R.id.tvHomeUserDepartment)
        val usn: TextView = findViewById(R.id.tvHomeUserUsn)

        val userDocRef = database?.collection("users")?.document(userEmail)
        userDocRef?.get()
            ?.addOnSuccessListener { userDocSnapshot ->
                val user: User? = userDocSnapshot.toObject(User::class.java)
                username.text = user?.username
                usn.text = user?.usn
                department.text = getDepartment(usn.text.toString())
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }
    }

    private fun getDepartment(usn: String): String {
        var department: String = ""
        if (usn.contains("CS", ignoreCase = true)) {
            department = "Computer Science"
        }
        if (usn.contains("EC", ignoreCase = true)) {
            department = "Electronics and Communication"
        }
        if (usn.contains("ME", ignoreCase = true)) {
            department = "Mechanical Engineering"
        }
        if (usn.contains("CV", ignoreCase = true)) {
            department = "Civil Engineering"
        }
        return department
    }
}