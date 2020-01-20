package com.example.prototype.ShareRidesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyshareRidesAdapter
import com.example.prototype.dataModels.CarSharingRidesDataModel

class ShareHistoryFragment: Fragment() {
    private lateinit var root: View
    private lateinit var progressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var ridesList: MutableList<CarSharingRidesDataModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_history, container, false)
        progressBar = root.findViewById(R.id.myRidesProgressBar)
        progressBar.visibility = View.VISIBLE
//        loadRides()


        ridesList = mutableListOf()
        //Getting Rides from database
        Util.getFirebaseFireStore().collection("myRides")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .get()
            .addOnSuccessListener {
                for(document in it.documents){
                    val myRides = document.toObject(CarSharingRidesDataModel::class.java)
                    ridesList.add(myRides!!)
                }
                mRecyclerView = root.findViewById(R.id.HistoryRecyclerView)
                mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                mRecyclerView.adapter = MyshareRidesAdapter(ridesList, root.context)
            }
        return root
    }

}