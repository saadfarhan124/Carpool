package com.example.prototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.dataModels.MyRides
import com.google.firebase.firestore.FirebaseFirestore


class MyridesAdapter(
    private val myRidesList: MutableList<MyRides>
) : RecyclerView.Adapter<MyridesAdapter.MyridesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyridesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_myrides_row, parent, false)
        return MyridesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return myRidesList.size
    }

    override fun onBindViewHolder(holder: MyridesViewHolder, position: Int) {
        val myRide = myRidesList[position]
        holder.bookingDate.text = myRide.bookingDate
        holder.bookingId.text = myRide.bookingId.toString()
        holder.totalFare.text = myRide.totalFare.toString()
        holder.startingTime.text = myRide.startingTime
        holder.endTime.text = myRide.endTime
        holder.pickUpPoint.text = myRide.pickUpPoint
        holder.dropOffPoint.text = myRide.dropOffPoint

        holder.btnDelete.setOnClickListener{
            FirebaseFirestore.getInstance().collection("rides")
                .document(myRide.rideId!!)
                .delete()
            myRidesList.removeAt(position)
            this.notifyItemRemoved(position)
            this.notifyItemRangeChanged(position, myRidesList.size)
            this.notifyDataSetChanged()
        }

    }


    inner class MyridesViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var bookingDate: TextView = view.findViewById(R.id.textViewDateOfRide)
        internal var bookingId: TextView = view.findViewById(R.id.textViewBookingID)
        internal var totalFare: TextView = view.findViewById(R.id.textViewFare)
        internal var startingTime: TextView = view.findViewById(R.id.textViewStartingTime)
        internal var endTime: TextView = view.findViewById(R.id.textViewEndTime)
        internal var pickUpPoint: TextView = view.findViewById(R.id.textViewPickUpPoint)
        internal var dropOffPoint: TextView = view.findViewById(R.id.textViewDropOffPoint)
        internal var btnDelete: TextView = view.findViewById(R.id.btnCancel)

    }
}

