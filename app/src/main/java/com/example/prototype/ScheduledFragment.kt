package com.example.prototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyridesAdapter
import com.example.prototype.dataModels.MyRides
import com.google.firebase.firestore.FirebaseFirestore

class ScheduledFragment : Fragment() {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: MyridesAdapter
    private lateinit var root: View
    private val TAG = "SeeRoutessss"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.activity_scheduled, container, false)
        progressBar = root.findViewById(R.id.myRidesProgressBar)
        progressBar.visibility = View.VISIBLE
        loadRides()
        return root
    }


    private fun loadRides() {
        db = FirebaseFirestore.getInstance()
        db.collection("rides")
            .whereEqualTo("customerID", Util.getGlobals().user!!.uid)
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    val myRides = mutableListOf<MyRides>()
                    for (document in it.result!!) {
                        val myRide = document.toObject(MyRides::class.java)
                        myRide.rideId = document.id
                        myRides.add(myRide)
                    }
                    if(myRides.size == 0){
                        Toast.makeText(root.context, "No Rides Found", Toast.LENGTH_SHORT).show()
                    }else{
                        mAdapter = MyridesAdapter(myRides, root.context,db)
                        mRecyclerView = root.findViewById(R.id.myridesRecyclerView)
                        val mLayoutManager = LinearLayoutManager(root.context)
                        mRecyclerView.layoutManager = mLayoutManager
                        mRecyclerView.itemAnimator = DefaultItemAnimator()
                        mRecyclerView.adapter = mAdapter
                    }
                    progressBar.visibility = View.INVISIBLE

                }else{
                    Toast.makeText(root.context, it.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}