package com.example.firebasedemologin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class PlantActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var userId: String? = null
    lateinit var reff: DatabaseReference
    lateinit var filePath :Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)
        auth = FirebaseAuth.getInstance() // user
        val currentUser = FirebaseAuth.getInstance().currentUser //tamnameJa
        userId = currentUser?.uid
        var database = FirebaseDatabase.getInstance().reference.child("Plant").child(userId.toString())
        var getstorage = FirebaseStorage.getInstance().getReference("Images/")
        var editImage = findViewById<ImageView>(R.id.editimageView)
        var namePlant = findViewById<TextView>(R.id.plantnameText)
        var dateofPlant = findViewById<TextView>(R.id.dateofplant)
        var dateOfStart = findViewById<TextView>(R.id.dateofstart)
        var infoAnnotationText = findViewById<TextView>(R.id.infoanno)

        var getdataImage = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var imageinfo = snapshot.child("imageUrl")
                editImage.

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }


        var getdataNamePlant = object : ValueEventListener{ // get data from firebase name
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                    var name1=snapshot.child("plantName").value.toString()
                     var name = database.child("plantName")
                    println(name)
                    println("---------------------------")
                println(name1)
                    namePlant.setText(name1)

//                for(i in snapshot.children){
//                    var name = i.child("plantName").getValue()
//
//                    //var phone1 = i.child("phone").getValue()
//                    sb.append("${i.key} \n$name ")
//                }
                println("************************")
               //namePlant.setText(sb).toString()
                //println(namePlant.setText(sb).toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(getdataNamePlant)
        database.addListenerForSingleValueEvent(getdataNamePlant)


        var getdataDateplant = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var datePlant = snapshot.child("date").value.toString()
                dateofPlant.setText(datePlant)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataDateplant)   //เรียกการใช้งาน
        //database.addListenerForSingleValueEvent(getdataDateplant)

        var getdataDateApp = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var datePlantApp = snapshot.child("dateApp").value.toString()
                dateOfStart.setText(datePlantApp)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataDateApp)   //เรียกการใช้งาน



        var getdataInfoAnnotation = object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var info = snapshot.child("anno").value.toString()
                infoAnnotationText.setText(info)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getdataInfoAnnotation)   //เรียกการใช้งาน
    }
}



