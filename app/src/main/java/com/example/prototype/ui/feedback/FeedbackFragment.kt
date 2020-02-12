package com.example.prototype.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.SuggestionDataModel
import org.jetbrains.anko.onClick

class FeedbackFragment:Fragment() {

    private lateinit var feedbackViewModel: FeedbackViewModel
    private lateinit var root:View
    private lateinit var btnSubmit: Button
    private lateinit var txtSuggest: TextView
    private lateinit var txtType: TextView
    private lateinit var progressBar:ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel =
            ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
         root = inflater.inflate(R.layout.fragment_feedback, container, false)
        val actv: AutoCompleteTextView = root.findViewById(R.id.outline_exposed_dropdown)
        val SggestionType = arrayOf("Driver", "Booking", "Ride Experience", "Application")


        val adapter = ArrayAdapter<String>(root.context, R.layout.activity_customspiner_item, SggestionType)

        val editTextFilledExposedDropdown = actv
        editTextFilledExposedDropdown.setAdapter(adapter)

        init()
        return root
    }

    private fun init(){
        btnSubmit = root.findViewById(R.id.btnSubmits)
        txtSuggest = root.findViewById(R.id.sg_txt_suggest)
        txtType = root.findViewById(R.id.outline_exposed_dropdown)
        progressBar = root.findViewById(R.id.loading)

        btnSubmit.onClick {
            progressBar.visibility = View.VISIBLE
//            var SuggestionDataModel: SuggestionDataModel? = null
            if(txtSuggest.text.isEmpty() || txtType.text.isEmpty()){
                Toast.makeText(root.context, "Please fill the above sections!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE

            }else{
                var suggest = SuggestionDataModel(txtSuggest.text.toString(),txtType.text.toString(), Util.getGlobals().user!!.uid)
                Util.getFirebaseFireStore().collection("feedback").document()
                    .set(suggest)
                    .addOnSuccessListener {
                        Toast.makeText(root.context, "Thank you for feedback", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.INVISIBLE
                        txtSuggest.text = ""
                        txtType.text = ""
                    }
                    .addOnFailureListener{Toast.makeText(root.context, "Error Something Wrong", Toast.LENGTH_SHORT).show()}
            }

        }
    }
}