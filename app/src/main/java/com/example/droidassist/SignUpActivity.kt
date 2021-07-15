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

class SignUpActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var db: FirebaseFirestore? = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        println("Auth $auth")
        println("DB: $db")
        val usernameTextField: TextInputLayout = findViewById(R.id.etUsername)
        val emailTextField: TextInputLayout = findViewById(R.id.etEmail)
        val usnTextField: TextInputLayout = findViewById(R.id.etUSN)
        val passwordTextField: TextInputLayout = findViewById(R.id.etPassword)
        val confirmPasswordTextField: TextInputLayout = findViewById(R.id.etConfirmPassword)

        val signUpButton: Button = findViewById(R.id.btnRegister)

        signUpButton.setOnClickListener {
            val username = usernameTextField.editText?.text.toString()
            val email = emailTextField.editText?.text.toString()
            val usn = usnTextField.editText?.text.toString()
            val password = passwordTextField.editText?.text.toString()
            val confirmPassword = confirmPasswordTextField.editText?.text.toString()

            if (TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(usn) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)
            ) {
                Toast.makeText(this, "Credentials Cannot be Empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val docRef = db?.collection("users")?.document(email)
                        println("docRef $docRef")
                        docRef?.get()?.addOnSuccessListener { documentSnapshot ->
                            val user: User? = documentSnapshot.toObject(User::class.java)
                            println("User $user")
                            if (user != null) {
                                Toast.makeText(this, "User Already Exists", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                val newUser = User(username, email, usn)
                                println("NewUser $newUser")
                                db?.collection("users")?.document(email)?.set(newUser)
                                Toast.makeText(
                                    this,
                                    "User Registered Successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong! ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }
}