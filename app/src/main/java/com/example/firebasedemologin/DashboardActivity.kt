package com.example.firebasedemologin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.*
class DashboardActivity : AppCompatActivity() {


    private var userId: String? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        auth = FirebaseAuth.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        var reff = FirebaseDatabase.getInstance().getReference()
        var showText = findViewById<TextView>(R.id.showtextView)
        var database = FirebaseDatabase.getInstance().reference.child("Users") //create path
        var showTextDb = findViewById<TextView>(R.id.showtextView)
        var logoutbtn = findViewById<Button>(R.id.btnlogout)
        var deletebtn = findViewById<Button>(R.id.deleteBtn)
        var editPlantBtn = findViewById<Button>(R.id.editplantBtn)
        var showPlantBtn = findViewById<Button>(R.id.showPlantBtn)
        val nameuser = currentUser?.email.toString()
        logoutbtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        userId = currentUser?.getUid()
        var user = findViewById<EditText>(R.id.user_editText)
        var email = findViewById<EditText>(R.id.name_editText)
        var password = findViewById<EditText>(R.id.phone_editText)
        var countchild = findViewById<TextView>(R.id.countTextView)
        var insertBtn = findViewById<Button>(R.id.insert_button)

        insertBtn.setOnClickListener {
            var usertext = user.text.toString()
            var emailtext = email.text.toString()
            var passwordtext = password.text.toString()
        //reff.child((maxid+1).toString()).setValue(Member(nametext,phonetext))
            database.child(auth.uid.toString()).setValue(User(usertext,emailtext,passwordtext))
            //database.child(nameuser).setValue(Member(nametext,phonetext))
            //database.child(user.toString()).setValue(Member(user,name,ph))

        }
        editPlantBtn.setOnClickListener {
            val intent = Intent(this,EditPlantActivity :: class.java)
            startActivity(intent)
        }

        showPlantBtn.setOnClickListener {
            val intent = Intent(this,PlantActivity :: class.java)
            startActivity(intent)
        }
        deletebtn.setOnClickListener {
            database.child(userId.toString()).removeValue()
        }
        showText.setOnClickListener {
            val name = currentUser?.displayName

            Toast.makeText(this,"showtext"+name.toString(),Toast.LENGTH_LONG).show()
        }
        var getdata = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children){
                    var usernamedb = i.child("name").getValue()
                    var emaildb = i.child("email").getValue()
                    var passworddb = i.child("password").getValue()
                    sb.append("${i.key} \n$usernamedb $emaildb\n $passworddb")
                }
                showTextDb.setText(sb)
            }
            override fun onCancelled(error: DatabaseError) {
            }

        }
        database.addValueEventListener(getdata)
        database.addListenerForSingleValueEvent(getdata)
        //showTextDb.text =
        //showText.text = nameuser.toString()

    }
}




