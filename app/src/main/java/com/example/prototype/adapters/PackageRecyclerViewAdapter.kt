package com.example.prototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.RequestsDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.example.prototype.dataModels.Routes


class PackageAdapter(private val requestsDataModel: List<RequestsDataModel>,
                     private val context: Context ): RecyclerView.Adapter<PackageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_package_row, parent, false)
        return PackageViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return requestsDataModel.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {

    }
}
class PackageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var startingPoint: TextView = v.findViewById(R.id.startingPoint)
    internal var startingTime: TextView = v.findViewById(R.id.startingTime)
    internal var endingPoint: TextView = v.findViewById(R.id.endingPoint)
    internal var endingTime: TextView = v.findViewById(R.id.endingTime)
    internal var seatsRemaining: TextView = v.findViewById(R.id.seatsRemaining)
    internal var distanceFromPickup: TextView = v.findViewById(R.id.distanceFromPickUp)
    internal var distanceFromDest: TextView = v.findViewById(R.id.distanceFromDropOff)
}