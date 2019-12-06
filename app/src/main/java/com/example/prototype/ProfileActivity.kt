package com.example.prototype

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.prototype.Utilities.Util
import com.google.android.material.card.MaterialCardView
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var updatePasswordCard: MaterialCardView
    private lateinit var logoutCard: MaterialCardView
    private lateinit var imageViewDisplayPicture: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhoneNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarprofile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        init()
    }

    private fun init() {
        Util.downloadDisplayPicture()


        updatePasswordCard = findViewById(R.id.materialCardViewPas)
        logoutCard = findViewById(R.id.materialCardViewLogout)

        imageViewDisplayPicture = findViewById(R.id.img_user)
        if(Util.getGlobals().userImage != null){
            imageViewDisplayPicture.setImageBitmap(Util.getGlobals().userImage)
        }
        imageViewDisplayPicture.setOnClickListener { launchGallery() }

        textViewName = findViewById(R.id.txt_name)
        textViewName.text = Util.getGlobals().user!!.displayName

        textViewEmail = findViewById(R.id.txt_email)
        textViewEmail.text = Util.getGlobals().user!!.email

        textViewPhoneNumber = findViewById(R.id.txt_num)
        textViewPhoneNumber.text = Util.getGlobals().user!!.phoneNumber

        updatePasswordCard.setOnClickListener {
            var intent = Intent(applicationContext, UpdatePassword::class.java)
            startActivity(intent)
        }
        logoutCard.setOnClickListener {
            startActivity(Util.logout(applicationContext))
        }

    }

    private fun launchGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, Util.getImageRequest())
    }

    private fun uploadImage() {
        val baos = ByteArrayOutputStream()
        val bitmap = Util.getGlobals().userImage
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = Util.getStorageRef().putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Util.downloadDisplayPicture()
            imageViewDisplayPicture.setImageBitmap(Util.getGlobals().userImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Util.getImageRequest() && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            } else {
                Util.getGlobals().imageUri = data.data
                Util.getGlobals().userImage = MediaStore.Images.Media.getBitmap(contentResolver, Util.getGlobals().imageUri)
                uploadImage()
            }
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
