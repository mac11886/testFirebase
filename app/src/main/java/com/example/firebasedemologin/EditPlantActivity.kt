package com.example.firebasedemologin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.nio.BufferUnderflowException

class EditPlantActivity : AppCompatActivity() {
    private var userId: String? = null
    private lateinit var auth: FirebaseAuth
    //lateinit var photo :ImageView
    lateinit var filepaath : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_plant)
        val currentUser = FirebaseAuth.getInstance().currentUser
        userId = currentUser?.getUid()
        auth = FirebaseAuth.getInstance()

        var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path
        var editNamePlant= findViewById<EditText>(R.id.editNameplant)
        var editDateStart = findViewById<EditText>(R.id.editDateStart)
        var editDateStartApp = findViewById<EditText>(R.id.editDatestartApp)
        var editAnno = findViewById<EditText>(R.id.editAnnotation)
        var saveInfoBtn  = findViewById<Button>(R.id.saveBtn)
        var chooseFile = findViewById<Button>(R.id.chooseBtn)
        var upload = findViewById<Button>(R.id.uploadbtn)
        var photo = findViewById<ImageView>(R.id.editimageView)

        saveInfoBtn.setOnClickListener {

        }

        chooseFile.setOnClickListener {
            filechooser()
        }
        upload.setOnClickListener {
            uploadFile()
        }

    }

    private fun uploadFile() {
        var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path
        if (filepaath!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()
            var imageRef = FirebaseStorage.getInstance().reference.child("images/"+userId+".jpg")
            //imageRef.getFile()
            imageRef.putFile(filepaath)
                .addOnSuccessListener { p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext, "File Upload", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener{p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext,p0.message,Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener {p0 ->
                    var progess = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                    pd.setMessage("Uploaded ${progess.toInt()}%")
                }
            println("------------------------------")
            println(imageRef.name)
            var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path
            var editNamePlant= findViewById<EditText>(R.id.editNameplant)
            var editDateStart = findViewById<EditText>(R.id.editDateStart)
            var editDateStartApp = findViewById<EditText>(R.id.editDatestartApp)
            var editAnno = findViewById<EditText>(R.id.editAnnotation)
            var namePlant = editNamePlant.text.toString()
            var dateStart = editDateStart.text.toString()
            var dateStartApp = editDateStartApp.text.toString()
            var anno = editAnno.text.toString()
            //var database = FirebaseDatabase.getInstance().reference.child("Plant") //create path

            database.child(userId.toString()).setValue(PlantInfo(namePlant,dateStart,dateStartApp,anno,imageRef.name))

            //filepaath = imageRef.
            //println(imageRef)

        }
    }

    private fun filechooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"Choose PHOTO"),111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var photo = findViewById<ImageView>(R.id.editimageView)
        if(requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepaath = data.data!!
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepaath)
            photo.setImageBitmap(bitmap)
        }
    }

}
