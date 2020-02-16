package com.example.prototype.ShareRidesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyshareRidesAdapter
import com.example.prototype.dataModels.CarSharingRidesDataModel

class ShareScheduledFragment : Fragment() {


    private lateinit var mRecyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar
    private lateinit var ridesList: MutableList<CarSharingRidesDataModel>
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
//        loadRides()

        ridesList = mutableListOf()
        //Getting Rides from database
        Util.getFirebaseFireStore().collection("Booking")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .whereEqualTo("rideStatus", "Booked")
            .get()
            .addOnSuccessListener {
                if (it.documents.size == 0) {
                    Toast.makeText(root.context, "No rides found", Toast.LENGTH_SHORT).show()
                } else {
                    for (document in it.documents) {
                        val myRides = document.toObject(CarSharingRidesDataModel::class.java)
                        myRides!!.bookingID = document.id
                        ridesList.add(myRides!!)
                    }
                    mRecyclerView = root.findViewById(R.id.myridesRecyclerView)
                    mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                    mRecyclerView.adapter = MyshareRidesAdapter(ridesList, root.context)
                }
                progressBar.visibility = View.INVISIBLE
            }
        return root
    }
}