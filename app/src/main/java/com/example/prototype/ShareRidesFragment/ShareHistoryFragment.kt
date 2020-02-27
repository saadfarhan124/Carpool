package com.example.prototype.ShareRidesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.example.prototype.R
import com.example.prototype.RidesFragments.HistoryFragment
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyshareRidesAdapter
import com.example.prototype.dataModels.CarSharingRidesDataModel

class ShareHistoryFragment: Fragment() {
    private lateinit var root: View
//    private lateinit var progressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var ridesList: MutableList<CarSharingRidesDataModel>
    private lateinit var shimmerRecyclerView: ShimmerRecyclerView
    private lateinit var noHistoy: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_history, container, false)
//        progressBar = root.findViewById(R.id.myRidesProgressBar)
        shimmerRecyclerView = root.findViewById(R.id.myshare_history_shimmer_recycler_view)
        noHistoy = root.findViewById(R.id.textViewHistory)
//        progressBar.visibility = View.VISIBLE
//        loadRides()


        ridesList = mutableListOf()
        //Getting Rides from database
        shimmerRecyclerView.visibility = View.VISIBLE
        Util.getFirebaseFireStore().collection("Booking")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .whereEqualTo("rideStatus", "completed")
            .get()
            .addOnSuccessListener {
                if (it.documents.size == 0) {
                    noHistoy.visibility = View.VISIBLE
                    noHistoy.text = "No History Found"
                    shimmerRecyclerView.visibility = View.INVISIBLE
                } else {
                    for (document in it.documents) {
                        val myRides = document.toObject(CarSharingRidesDataModel::class.java)
                        myRides!!.bookingID = document.id
                        ridesList.add(myRides!!)
                    }
                    mRecyclerView = root.findViewById(R.id.HistoryRecyclerView)
                    mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                    mRecyclerView.adapter = MyshareRidesAdapter(ridesList, root.context)
                }
                shimmerRecyclerView.visibility = View.INVISIBLE

            }
        return root
    }

}