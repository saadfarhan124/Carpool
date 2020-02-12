package com.example.prototype

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.ReportDataModel
import org.jetbrains.anko.onClick

class ErrorActivity:AppCompatActivity(){

    private lateinit var btnSubmit: Button
    private lateinit var txtSuggest: TextView
    private lateinit var txtType: TextView
    private lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        val actv: AutoCompleteTextView = findViewById(R.id.outline_exposed_dropdown)
        val SggestionType = arrayOf("Driver", "Booking", "Ride Experience", "Application")


        val adapter = ArrayAdapter<String>(this, R.layout.activity_customspiner_item, SggestionType)

        val editTextFilledExposedDropdown = actv
        editTextFilledExposedDropdown.setAdapter(adapter)
        init()

    }

    private fun init(){
        btnSubmit = findViewById(R.id.btnSubmits)
        txtSuggest = findViewById(R.id.sg_txt_suggest)
        txtType = findViewById(R.id.outline_exposed_dropdown)
        progressBar = findViewById(R.id.loading)
        btnSubmit.onClick {
            progressBar.visibility = View.VISIBLE
           if(txtSuggest.text.isEmpty() || txtType.text.isEmpty()){
               Toast.makeText(this, "Please fill the above sections!", Toast.LENGTH_SHORT).show()
               progressBar.visibility = View.INVISIBLE
           }else{
               var report = ReportDataModel(txtSuggest.text.toString(),txtType.text.toString(),  Util.getGlobals().user!!.uid)
               Util.getFirebaseFireStore().collection("report").document()
                   .set(report)
                   .addOnSuccessListener {
                       Toast.makeText(this, "Thank you for reporting", Toast.LENGTH_SHORT).show()
                       txtSuggest.text = ""
                       txtType.text = ""
                       progressBar.visibility = View.INVISIBLE
                   }
                   .addOnFailureListener{ Toast.makeText(this, "Error Something Wrong", Toast.LENGTH_SHORT).show()}
           }

        }
    }

}