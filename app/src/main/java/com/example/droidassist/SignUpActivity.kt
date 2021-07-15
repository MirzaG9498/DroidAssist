package com.example.droidassist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

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

            println(username)
            println(email)
            println(usn)
            println(password)
            println(confirmPassword)
        }

    }
}