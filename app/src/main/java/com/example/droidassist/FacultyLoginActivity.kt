package com.example.droidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class FacultyLoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_login)
        val emailTextField: TextInputLayout = findViewById(R.id.etFacultyLoginEmail)
        val passwordTextField: TextInputLayout = findViewById(R.id.etFacultyLoginPassword)

        val loginButton: Button = findViewById(R.id.btnFacultyLogin)

        loginButton.setOnClickListener {
            val email = emailTextField.editText?.text.toString()
            val password = passwordTextField.editText?.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Credentials Cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, FacultyHomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }
}