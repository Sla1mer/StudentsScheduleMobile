package com.example.studentsschedulemobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText;
    private lateinit var editTextPass: EditText;
    private lateinit var signInBtn: Button;
    private lateinit var regBtn: Button;
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance("https://studentssheduledb-default-rtdb.europe-west1.firebasedatabase.app/").reference


    companion object {
        lateinit var auth: FirebaseAuth;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        signInBtn = findViewById(R.id.signInBtn);
        regBtn = findViewById(R.id.regBtn);

        signInBtn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPass.text.toString()

            signIn(email, password)
        }

        regBtn.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }


    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty())
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(this, "Вы авторизовались", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}