package com.example.firebasedemologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        var btnRegister=findViewById<Button>(R.id.btnRegister)




        btnRegister.setOnClickListener {
            signUpUser()
        }

    }

    private fun signUpUser(){
        var registerEmail=findViewById<TextView>(R.id.registerEmail)
        var registerPassword=findViewById<TextView>(R.id.registerPassword)
        var registerUsername=findViewById<TextView>(R.id.registerUsername)
        var datebase =FirebaseDatabase.getInstance().reference
        var reff = FirebaseDatabase.getInstance().reference.child("Member")
        var maxid = 0

        if(registerEmail.text.toString().isEmpty()){
            registerEmail.error = "Please enter email"
            registerEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher((registerEmail.text.toString())).matches()){
            registerEmail.error= "Please enter valid email"
            return
        }
        if(registerPassword.text.toString().isEmpty()){
            registerPassword.error= "Please enter password"
            registerPassword.requestFocus()
            return
        }
        if (registerUsername.text.toString().isEmpty()){
            registerUsername.error = "Please enter Unique"
            return
        }

        var user = registerUsername.text.toString()
        datebase.child(user).setValue(Member("",""))
        auth.createUserWithEmailAndPassword(registerEmail.text.toString(), registerPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        var reff = FirebaseDatabase.getInstance().reference.child("Member")

                        reff.child("member")
                        reff.child((maxid+1).toString()).setValue("member")
                       // user!!.sendEmailVerification()
                           // .addOnCompleteListener { task ->
                               // if (task.isSuccessful) {
                                    val intent = Intent(this,MainActivity :: class.java)
                                    startActivity(intent)
                                    Log.e("Action","Successful")
                                    finish()
                               // }
                            }

                    else { Toast.makeText(baseContext,"Sign Up Failed. Try again",Toast.LENGTH_LONG).show()
                    }

                        //Toast.makeText(baseContext,"Sign Up Failed. Try again",Toast.LENGTH_LONG).show()
                    }

                    // ...
                }
    }

