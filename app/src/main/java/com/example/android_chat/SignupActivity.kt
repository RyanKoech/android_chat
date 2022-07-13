package com.example.android_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android_chat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var editName : EditText
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        editEmail = findViewById(R.id.edit_email)
        editPassword = findViewById(R.id.edit_password)
        btnSignup = findViewById(R.id.btn_signup)
        editName = findViewById(R.id.edit_name)

        btnSignup.setOnClickListener {
            val email =editEmail.text.toString()
            val password = editPassword.text.toString()
            val name = editName.text.toString()

            signUp(name, email, password)
        }
    }

    private fun signUp(name: String, email : String, password : String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //Go to home activity
                    addUserToDatabase(name, email, mAuth.currentUser!!.uid)
                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignupActivity, "Some error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name :String, email : String, uid : String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}