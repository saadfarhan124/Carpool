package com.example.prototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.dataModels.CarSharingRidesDataModel


class MyshareRidesAdapter(private val ridesList: MutableList<CarSharingRidesDataModel>,
                          private val context:Context): RecyclerView.Adapter<MyshareRidesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyshareRidesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_myrides_share_row, parent, false)
        return MyshareRidesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return ridesList.size
    }

    override fun onBindViewHolder(holder: MyshareRidesViewHolder, position: Int) {
        val ride = ridesList[position]
        holder.textViewDate.text = ride.date
        holder.textViewPickUpAddress.text = ride.pickUpAddress
        holder.textViewDropOffAddress.text = ride.dropOffAddress
        holder.textViewPickUpTime.text = ride.pickUpTime
        holder.textViewDropOffTime.text = ride.dropOffTime
        holder.textViewStatus.text = ride.rideStatus
    }
}
class MyshareRidesViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var textViewDate: TextView = v.findViewById(R.id.textViewDate)
    internal var textViewPickUpAddress: TextView = v.findViewById(R.id.textViewPickUpAddress)
    internal var textViewDropOffAddress: TextView = v.findViewById(R.id.textViewDropOffAddress)
    internal var textViewPickUpTime: TextView = v.findViewById(R.id.textViewPickUpTime)
    internal var textViewDropOffTime: TextView = v.findViewById(R.id.textViewDropOffTime)
    internal var textViewStatus: TextView = v.findViewById(R.id.textViewStatus)
}