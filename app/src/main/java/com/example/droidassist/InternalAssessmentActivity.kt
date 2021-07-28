package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import models.*

class InternalAssessmentActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val firestore = FirebaseFirestore.getInstance()
    private var usn: String? = null
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_assessment)

        val spinner: Spinner = findViewById(R.id.spinnerInternalAssessment)
        spinner.onItemSelectedListener = this

        usn = intent.getStringExtra("usn")
        println(usn)
        recyclerView = findViewById(R.id.rvInternal)
    }

    private fun createInternalScores(iaType: String): List<InternalAssessment> {
        var internalScoreList = mutableListOf<InternalAssessment>()
        val userDocRef = firestore.collection("users").document(usn!!)
        val iaCollectionRef = firestore.collection("ia")

        userDocRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                user = it.result?.toObject(User::class.java)
                println(user)
                println(user?.branch)
                iaCollectionRef.whereEqualTo("branch", user?.branch)
                    .whereEqualTo("sem", user?.sem).whereEqualTo("sec", user?.sec)
                    .whereEqualTo("usn", user?.usn).whereEqualTo("type", iaType)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null || snapshot == null) {
                            Toast.makeText(
                                this,
                                "Empty List Or Error: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            internalScoreList = snapshot.toObjects(InternalAssessment::class.java)
                            internalScoreList.sortBy { ia ->
                                ia.subjectCode
                            }
                            recyclerView.adapter = InternalsAdapter(this, internalScoreList)
                            recyclerView.layoutManager = LinearLayoutManager(this)
                            // recyclerView.adapter?.notifyDataSetChanged()
                            for (internal in internalScoreList) {
                                println("internal $internal")
                            }
                        }
                    }
            }
        }

//        for (i in 1..100) {
//            internalScoreList.add(
//                InternalAssessment("4JN18CS$i", "18CS$i", "$i")
//            )
//        }
        return internalScoreList
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val iaType = parent?.getItemAtPosition(position).toString()
        createInternalScores(iaType)
        println(iaType)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        println(parent)
    }
}