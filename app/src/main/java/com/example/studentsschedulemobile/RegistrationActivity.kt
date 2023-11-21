package com.example.studentsschedulemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText;
    private lateinit var editTextPass: EditText;
    private lateinit var regBtn: Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        regBtn = findViewById(R.id.regBtn);

        regBtn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPass.text.toString()

            registation(email, password)
        }
    }

    private fun registation(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty())
            AuthActivity.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Вы зарегистрировались", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}