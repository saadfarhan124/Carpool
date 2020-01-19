package com.example.prototype.ui.mysharerides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyshareRidesAdapter
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.CarSharingRidesDataModel
import com.google.android.gms.maps.model.UrlTileProvider

class MyshareRidesFragment: Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var ridesList: MutableList<CarSharingRidesDataModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_myshare_rides, container, false)
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
                mRecyclerView = root.findViewById(R.id.myridesShareRecyclerView)
                mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                mRecyclerView.adapter = MyshareRidesAdapter(ridesList, root.context)
            }
        return root
    }

}