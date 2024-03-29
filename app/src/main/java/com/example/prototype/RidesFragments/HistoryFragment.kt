package com.example.prototype.RidesFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyridesAdapter
import com.example.prototype.dataModels.Booking
import com.example.prototype.dataModels.MyRides
import com.google.firebase.firestore.FirebaseFirestore

class HistoryFragment : Fragment() {

    private lateinit var root: View
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MyridesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_history, container, false)
        progressBar = root.findViewById(R.id.myRidesProgressBar)
        progressBar.visibility = View.VISIBLE
        loadRides()
        return root
    }

    private fun loadRides() {
        db = FirebaseFirestore.getInstance()
        db.collection("booking")
            .whereEqualTo("customerID", Util.getGlobals().user!!.uid)
            .whereEqualTo("rideStatus", "completed")
            .get()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    val bookings = mutableListOf<Booking>()
                    for (document in it.result!!) {
                        val booking = document.toObject(Booking::class.java)
                        booking.id = document.id
                        bookings.add(booking)
                    }
                    if(bookings.size == 0){
                        Toast.makeText(root.context, "No Rides Found", Toast.LENGTH_SHORT).show()
                    }else{
                        mAdapter = MyridesAdapter(bookings, root.context,db , "history")
                        mRecyclerView = root.findViewById(R.id.HistoryRecyclerView)
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