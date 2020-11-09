package com.example.firebasedemologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {
    private lateinit var text_input: String
    private lateinit var text_password: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        var plantBtn = findViewById<Button>(R.id.plantBtn)
        var btnLogin=findViewById<Button>(R.id.btnLogin)
        var inputEmail=findViewById<EditText>(R.id.inputEmail)
        var inputPassword=findViewById<EditText>(R.id.inputPassword)

        var signupBtn = findViewById<Button>(R.id.signupBtn)
        var signup = findViewById<TextView>(R.id.signUp)

//        btnLogin.setOnClickListener {
//            text_input = inputEmail.text.toString()
//            text_password = inputPassword.text.toString()
//            if(text_input=="mac" && text_password=="123"){
//                Toast.makeText(this, "Loggin Success", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                Toast.makeText(this,"Fail try again",Toast.LENGTH_LONG).show()
//            }
//        }

        plantBtn.setOnClickListener {
            val intent = Intent(this,PlantActivity :: class.java)
            startActivity(intent)
        }
        signupBtn.setOnClickListener {
            val intent = Intent(this,RegisterActivity :: class.java)
            startActivity(intent)
        }

        signup.setOnClickListener {
            val intent = Intent(this,RegisterActivity :: class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            doLogin()
        }


    }

    private fun doLogin() {
        var inputEmail=findViewById<EditText>(R.id.inputEmail)
        var inputPassword=findViewById<EditText>(R.id.inputPassword)


        if(inputEmail.text.toString().isEmpty()){
            inputEmail.error = "Please enter email"
            inputEmail.requestFocus()
        }
        if(!Patterns.EMAIL_ADDRESS.matcher((inputEmail.text.toString())).matches()){
            inputEmail.error= "Please enter valid email"
        }
        if(inputPassword.text.toString().isEmpty()){
            inputPassword.error= "Please enter password"
            inputPassword.requestFocus()
        }
        auth.signInWithEmailAndPassword(inputEmail.text.toString(), inputPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.e("Action","success")
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(this, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                    // ...
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser : FirebaseUser?){
       if (currentUser != null){
          // if(currentUser.isEmailVerified){
           val intent = Intent(this,DashboardActivity :: class.java)
           startActivity(intent)
           finish()}
           //else{
            //   Toast.makeText(baseContext, "Verified your email .",
              //     Toast.LENGTH_SHORT).show()

       //    }
        //}else{
//            Toast.makeText(baseContext, "Login failed.",
//                Toast.LENGTH_SHORT).show()


    }





}