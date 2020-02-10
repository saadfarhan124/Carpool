package com.example.prototype

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.UserDataModel
import com.skyfishjy.library.RippleBackground
import kotlinx.android.synthetic.main.activity_gender.*
import org.jetbrains.anko.onClick
import java.time.LocalDate
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class SelectGenderAndDob : AppCompatActivity() {

    //Ripples
    private lateinit var maleRippleBackground: RippleBackground
    private lateinit var femaleRippleBackground: RippleBackground

    //Image VIews
    private lateinit var maleImageView: ImageView
    private lateinit var femaleImageView: ImageView

    //Boolean Flags
    private var maleFlag = false
    private var femaleFlag = false

    //Date of Birth Edit Text
    private lateinit var editTextDob:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)
        init()


    }

    private fun init(){
        //Ripple Backgrounds
        femaleRippleBackground = findViewById(R.id.content)
        maleRippleBackground = findViewById(R.id.contentMale)

        //Male Image View
        maleImageView = findViewById(R.id.img_male)
        maleImageView.setImageResource(R.mipmap.ic_gender)
        maleImageView.onClick {
            if(femaleFlag){
                femaleRippleBackground.stopRippleAnimation()
                femaleFlag = false
            }
            maleRippleBackground.startRippleAnimation()
            maleFlag = true
        }

        //Female Image View
        femaleImageView = findViewById(R.id.img_female)
        femaleImageView.setImageResource(R.mipmap.ic_female)
        femaleImageView.onClick {
            if(maleFlag){
                maleRippleBackground.stopRippleAnimation()
                maleFlag = false
            }
            femaleRippleBackground.startRippleAnimation()
            femaleFlag = true
        }


        var btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.onClick {
            if(!femaleFlag && !maleFlag){
                Toast.makeText(applicationContext, "Please select a gender", Toast.LENGTH_SHORT).show()
            }else if(editTextDob.text.isNullOrEmpty()){
                Toast.makeText(applicationContext, "Please select date of birth", Toast.LENGTH_LONG).show()
            }else{
                var userDataModel: UserDataModel? = null
                if(maleFlag){
                    userDataModel = UserDataModel(editTextDob.text.toString(), "Male", Util.getGlobals().user!!.email!!,
                        Util.getGlobals().user!!.displayName!!,Util.getGlobals().user!!.phoneNumber!!)
                }else{
                    userDataModel = UserDataModel(editTextDob.text.toString(), "Female", Util.getGlobals().user!!.email!!,
                        Util.getGlobals().user!!.displayName!!,Util.getGlobals().user!!.phoneNumber!!)
                }
                Util.getFirebaseFireStore().collection("users")
                    .document(Util.getFirebaseAuth().currentUser!!.uid)
                    .set(userDataModel)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Registered successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    }
            }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR )
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        editTextDob = findViewById(R.id.editTextDob)
        editTextDob.onClick {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                editTextDob.setText("$dayOfMonth/${month+1}/$year")
            }, year, month, day)
            //2001 in milliseconds
            datePickerDialog.datePicker.maxDate = 1009738800000
            datePickerDialog.show()
        }
    }
}
