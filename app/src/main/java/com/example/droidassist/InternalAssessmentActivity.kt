package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Internals
import models.InternalsAdapter
import models.Resources
import models.ResourcesAdapter

class InternalAssessmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_assessment)

        val internalScoresList = createInternalScores()
        val recyclerView: RecyclerView = findViewById(R.id.rvInternal)
        recyclerView.adapter = InternalsAdapter(this, internalScoresList)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun createInternalScores(): List<Internals> {
        val internalScoreList = mutableListOf<Internals>()
        for (i in 1..100) {
            internalScoreList.add(
               Internals("4JN18CS$i", "18CS$i", "$i")
            )
        }
        return internalScoreList
    }
}