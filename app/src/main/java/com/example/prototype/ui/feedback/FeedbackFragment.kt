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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel =
            ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
         root = inflater.inflate(R.layout.fragment_feedback, container, false)
        val actv: AutoCompleteTextView = root.findViewById(R.id.outline_exposed_dropdown)
        val SggestionType = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")


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

        btnSubmit.onClick {
//            var SuggestionDataModel: SuggestionDataModel? = null
            var suggest = SuggestionDataModel(txtSuggest.text.toString(),txtType.text.toString())
            Util.getFirebaseFireStore().collection("feedback")

                .add(suggest)
                .addOnSuccessListener {
                    Toast.makeText(root.context, "DocumentSnapshot successfully written!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{Toast.makeText(root.context, "Error writing document", Toast.LENGTH_SHORT).show()}
        }
    }
}