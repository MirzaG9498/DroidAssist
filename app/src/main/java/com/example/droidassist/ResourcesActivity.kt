package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Resources
import models.ResourcesAdapter

class ResourcesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        val resourcesList = createResources()
        val recyclerView: RecyclerView = findViewById(R.id.rvResources)
        recyclerView.adapter = ResourcesAdapter(this, resourcesList)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun createResources(): List<Resources> {
        val resourcesList = mutableListOf<Resources>()
        for (i in 1..100) {
            resourcesList.add(
                Resources(
                    "18CS$i",
                    "https://www.youtube.com/playlist?list=PL7NYbSE8uaBBSIHntxHhyD_Fy5NUznAaD",
                    "https://meet.google.com/qrh-roqm-ube",
                    "https://drive.google.com/drive/folders/1DUipWD5PPaY16VFy076ZHphw9FU2j9tu"
                )
            )
        }
        return resourcesList
    }
}