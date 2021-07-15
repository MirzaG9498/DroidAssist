package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginText: TextView = findViewById(R.id.loginText)
        val intent = intent.getStringExtra("Zaheer")
        loginText.text = intent

    }
}