package com.example.prototype

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.InvoiceDataModel
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jetbrains.anko.onClick
import java.io.ByteArrayOutputStream

class DespositSlipUploadActivity : AppCompatActivity() {

    //TextViews
    private lateinit var editTextDepositSlipNumber: TextView
    private lateinit var amountTextView: TextView

    //Button
    private lateinit var btnUploadSlip: Button

    //ImageView
    private lateinit var slipUploadImageView: ImageView

    //Bitmap
    private lateinit var depositImage: Bitmap

    //Progress Bar
    private lateinit var depositSlipProgressBar: ProgressBar

    //Request ID
    private var requestID: String = ""

    //Image flag
    private var imageUploaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_transfer)
        AndroidThreeTen.init(this)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarsp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        init()
    }


    fun init() {

        requestID = intent.extras!!.get("requestID").toString()


        editTextDepositSlipNumber = findViewById(R.id.editTextDepositSlipNumber)
        amountTextView = findViewById(R.id.amountTextView)

        slipUploadImageView = findViewById(R.id.slipUploadImageView)
        slipUploadImageView.onClick {
            var alert = Util.getAlertDialog(this)
            alert.setMessage("Do you want to open camera or launch gallery")
            alert.setPositiveButton("Gallery"){_, _ ->
                launchGallery()
            }
            alert.setNegativeButton("Camera"){_,_ ->
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, Util.getCameraCode())
                    }
                }
            }

            alert.show()
        }

        depositSlipProgressBar = findViewById(R.id.depositSlipProgressBar)

        btnUploadSlip = findViewById(R.id.btnUploadSlip)
        btnUploadSlip.onClick {
            if (!imageUploaded) {
                Toast.makeText(
                    applicationContext,
                    "Please upload an image first",
                    Toast.LENGTH_SHORT
                ).show()
            }else if(amountTextView.text.isNullOrEmpty() || editTextDepositSlipNumber.text.isNullOrEmpty()){
                Toast.makeText(
                    applicationContext,
                    "Please fill the required fields first",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                depositSlipProgressBar.visibility = View.VISIBLE
                Util.getFirebaseFireStore().collection("invoiceNumber")
                    .get()
                    .addOnSuccessListener {
                        var invoiceID = it.documents[0]["invoiceNumber"].toString().toInt()
                        Util.getFirebaseFireStore().collection("invoiceNumber")
                            .document(it.documents[0].id).update("invoiceNumber", invoiceID + 1)
                            .addOnSuccessListener {
                                var invoive = InvoiceDataModel(
                                    invoiceID,
                                    editTextDepositSlipNumber.text.toString(),
                                    amountTextView.text.toString().toInt(),
                                    Util.getFormattedDate(),
                                    Util.getGlobals().user!!.uid,
                                    requestID
                                )
                                Util.getFirebaseFireStore().collection("invoices")
                                    .add(invoive)
                                    .addOnSuccessListener {
                                        Util.getFirebaseFireStore().collection("carRideRequests")
                                            .document(requestID)
                                            .update("requestStatus", "Payment Processing")
                                            .addOnSuccessListener {
                                                uploadImage(depositImage, requestID.toInt())
                                            }
                                    }
                            }


                    }
            }

        }
    }

    private fun launchGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, Util.getImageRequest())
    }

    private fun uploadImage(depositImage: Bitmap, requestID: Int) {
        val baos = ByteArrayOutputStream()
        val bitmap = depositImage
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask =
            Util.getStorageRefDepositSlip(requestID)
                .putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT)
                .show()
        }.addOnSuccessListener {
            Toast.makeText(
                applicationContext,
                "Invoice verification pending",
                Toast.LENGTH_LONG
            ).show()
            depositSlipProgressBar.visibility = View.INVISIBLE
            startActivity(Intent(applicationContext, navdrawer::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Util.getImageRequest() && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            } else {
                depositImage = MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    data.data
                )
                slipUploadImageView.setImageBitmap(depositImage)
                imageUploaded = true
            }
        }
        //Check for camera
        else if(requestCode == Util.getCameraCode() && resultCode == Activity.RESULT_OK){
            depositImage = data!!.extras!!.get("data") as Bitmap
            slipUploadImageView.setImageBitmap(depositImage)
            imageUploaded = true

        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
