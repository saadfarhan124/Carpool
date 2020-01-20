package com.example.prototype

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.google.android.material.card.MaterialCardView
import org.jetbrains.anko.onClick
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var updatePasswordCard: MaterialCardView
    private lateinit var logoutCard: MaterialCardView
    private lateinit var dobCard:MaterialCardView
    private lateinit var imageViewDisplayPicture: ImageView
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewPhoneNumber: TextView
    private lateinit var textViewGender: TextView
    private lateinit var textViewPhoneDob: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var imageViewNumberIcon: ImageView
    private lateinit var imageViewEmailIcon: ImageView
    private lateinit var imageViewPasswordIcon: ImageView
    private lateinit var imageViewGenderIcon: ImageView
    private lateinit var imageViewDobIcon: ImageView
    private lateinit var imageViewLogoutIcon: ImageView


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

        updatePasswordCard = findViewById(R.id.materialCardViewPas)
        logoutCard = findViewById(R.id.materialCardViewLogout)
        dobCard = findViewById(R.id.materialCardViewDOB)

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

        textViewGender = findViewById(R.id.txt_gender)
        textViewGender.text = Util.getGlobals().userDataModel!!.gender

        textViewPhoneDob = findViewById(R.id.txt_dob)
        textViewPhoneDob.text = Util.getGlobals().userDataModel!!.dateOfBirth

        updatePasswordCard.setOnClickListener {
            var intent = Intent(applicationContext, UpdatePassword::class.java)
            startActivity(intent)
        }
        logoutCard.setOnClickListener {
            startActivity(Util.logout(applicationContext))
        }
        dobCard.onClick {
            var calender = Util.getCalendarInstance()
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                textViewPhoneDob.text = ("$dayOfMonth/${month+1}/$year")
                Util.getFirebaseFireStore().collection("users")
                    .document(Util.getGlobals().user!!.uid)
                    .update("dateOfBirth", textViewPhoneDob.text)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_SHORT).show()
                    }
            }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH))
            //2001 in milliseconds
            datePickerDialog.datePicker.maxDate = 1009738800000
            datePickerDialog.show()
        }

        progressBar = findViewById(R.id.progressBarProfile)

//        imageViewNumberIcon = findViewById(R.id.img_num)
//        imageViewNumberIcon.setImageResource(R.drawable.ic_phone)
//
//        imageViewEmailIcon = findViewById(R.id.img_email)
//        imageViewEmailIcon.setImageResource(R.drawable.ic_email)
//
//        imageViewPasswordIcon = findViewById(R.id.img_pas)
//        imageViewPasswordIcon.setImageResource(R.drawable.ic_changepass)
//
//        imageViewGenderIcon = findViewById(R.id.img_gen)
//        imageViewGenderIcon.setImageResource(R.drawable.ic_gender)
//
//        imageViewDobIcon = findViewById(R.id.img_dob)
//        imageViewDobIcon.setImageResource(R.drawable.ic_dob)
//
//        imageViewLogoutIcon = findViewById(R.id.img_logout)
//        imageViewLogoutIcon.setImageResource(R.drawable.ic_logout)
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
            imageViewDisplayPicture.setImageBitmap(Util.getGlobals().userImage)
            progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Util.getImageRequest() && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            } else {
                progressBar.visibility = View.VISIBLE
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

    override fun onBackPressed() {
        intent = Intent(applicationContext, navdrawer::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
