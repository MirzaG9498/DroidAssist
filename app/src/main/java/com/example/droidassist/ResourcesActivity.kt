package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import models.*

class ResourcesActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance()
    private var usn: String? = null
    private var user: User? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        usn = intent.getStringExtra("usn")
        val resourcesList = createResources()
        recyclerView = findViewById(R.id.rvResources)
    }

    private fun createResources(): List<Resources> {
        var resourcesList = mutableListOf<Resources>()

        val userDocRef = firestore.collection("users").document(usn!!)
        val resourcesCollectionRef = firestore.collection("resources")
        userDocRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                user = it.result?.toObject(User::class.java)
                println(user)
                println(user?.branch)
                resourcesCollectionRef.whereEqualTo("branch", user?.branch)
                    .whereEqualTo("sem", user?.sem).whereEqualTo("sec", user?.sec)
                    .orderBy("subjectCode", Query.Direction.ASCENDING)
                    .get().addOnSuccessListener { result ->
                        resourcesList = result.toObjects(Resources::class.java)
                        recyclerView.adapter = ResourcesAdapter(this, resourcesList)
                        recyclerView.layoutManager = LinearLayoutManager(this)
//                    recyclerView.adapter?.notifyDataSetChanged()
                        for (resource in resourcesList) {
                            println("Resource $resource")
                        }
                    }
            } else {
                println("Error: $it")
            }
        }


//        for (i in 1..100) {
//            resourcesList.add(
//                Resources(
//                    "18CS$i",
//                    "https://www.youtube.com/playlist?list=PL7NYbSE8uaBBSIHntxHhyD_Fy5NUznAaD",
//                    "https://meet.google.com/qrh-roqm-ube",
//                    "https://drive.google.com/drive/folders/1DUipWD5PPaY16VFy076ZHphw9FU2j9tu"
//                )
//            )
//        }
        return resourcesList
    }
}