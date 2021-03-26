package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        register()
    }

    private fun register() {
        val registerButton = findViewById<Button>(R.id.registerButton)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val pwdInput = findViewById<EditText>(R.id.pwdInput)
        val firstNameInput = findViewById<EditText>(R.id.firstNameInput)
        val lastNameInput = findViewById<EditText>(R.id.lastNameInput)
        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(emailInput.text.toString(),pwdInput.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                        currentUserDb?.child("firstname")?.setValue(firstNameInput.text.toString())
                        currentUserDb?.child("lastname")?.setValue(lastNameInput.text.toString())
                        Toast.makeText(this@Register, "Registration Successful!", Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this@Register, "Registration failed! Please try again", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}