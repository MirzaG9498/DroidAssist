package com.example.droidassist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import models.User

class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseFirestore? = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val emailTextField: TextInputLayout = findViewById(R.id.etLoginEmail)
        val passwordTextField: TextInputLayout = findViewById(R.id.etLoginPassword)
        val usnTextField: TextInputLayout = findViewById(R.id.etLoginUsn)

        val loginButton: Button = findViewById(R.id.btnLogin)

        val usnRegex = Regex("^[0-9]{1,2}[a-z]{2}[0-9]{2}[a-z]{2}[0-9]{3}\$", RegexOption.IGNORE_CASE)


        loginButton.setOnClickListener {
            val email = emailTextField.editText?.text.toString()
            val password = passwordTextField.editText?.text.toString()
            val usn = usnTextField.editText?.text.toString().uppercase()
            var falseUsn: Boolean = false

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(usn)) {
                Toast.makeText(this, "Credentials Cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(!usnRegex.matches(usn)){
                Toast.makeText(this, "Please Enter Valid USN", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val userDocRef = database?.collection("users")?.document(usn)
            userDocRef?.get()
                ?.addOnSuccessListener { userDocSnapshot ->
                    val user: User? = userDocSnapshot.toObject(User::class.java)
                    println(email)
                    println(user)
                    println(usn)
                    if(email != user?.email || usn != user.usn){
                        println(email)
                        println(user)
                        println(usn)
                        falseUsn = true
                        println(falseUsn)
                    }

                    if(falseUsn){
                        println("What is")
                        Toast.makeText(this, "Please Enter Valid Credentials", Toast.LENGTH_LONG).show()
                        return@addOnSuccessListener
                    } else {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this,HomeActivity::class.java)
                                    intent.putExtra("userUsn",usn)
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
                ?.addOnFailureListener { e ->
                    Toast.makeText(this, "Something went wrong! $e", Toast.LENGTH_LONG).show()
                    return@addOnFailureListener
                }
        }
    }
}