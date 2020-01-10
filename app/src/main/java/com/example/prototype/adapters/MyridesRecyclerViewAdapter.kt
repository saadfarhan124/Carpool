package com.example.prototype.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.TrackRideActivity
import com.example.prototype.dataModels.Booking
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.onClick


class MyridesAdapter(
    private val bookingList: MutableList<Booking>,
    private val context: Context,
    private val db: FirebaseFirestore,
    private val type: String = "scheduled"
) : RecyclerView.Adapter<MyridesAdapter.MyridesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyridesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_myrides_row, parent, false)
        return MyridesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: MyridesViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.bookingDate.text = booking.bookingDate
        holder.bookingId.text = booking.bookingId.toString()
        holder.totalFare.text = booking.totalFare.toString()
        holder.startingTime.text = booking.startingTime
        holder.endTime.text = booking.endingTime
        holder.pickUpPoint.text = booking.pickUpSpotName
        holder.dropOffPoint.text = booking.dropOffSpotName
        when (type) {
            "history" -> {
                holder.btnDelete.visibility = View.INVISIBLE
                holder.btnTrack.visibility = View.INVISIBLE
            }
        }

        //Delete Ride
        holder.btnDelete.setOnClickListener {
            val confirmDialog =
                AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog)
            confirmDialog.setTitle("Sath Chaloo")
            confirmDialog.setMessage("Are you sure you want to cancel this ride?")
            confirmDialog.setPositiveButton("Yes") { _, _ ->

                bookingList.removeAt(position)
                db.collection("booking")
                    .document(booking.bookingId!!.toString())
                    .delete()
                    .addOnCompleteListener { taskDeleteFromMyRides ->
                        this.notifyItemRemoved(position)
                        this.notifyItemRangeChanged(position, bookingList.size)
                        this.notifyDataSetChanged()
                    }
            }
            confirmDialog.setNegativeButton("No") { _, _ -> }
            confirmDialog.show()
        }

        //Track Ride
        holder.btnTrack.onClick {
            var intent = Intent(context, TrackRideActivity::class.java)
            intent.putExtra("routeId", booking.routeId)
            context.startActivity(intent)
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
        internal var btnTrack: TextView = view.findViewById(R.id.btnTrack)

    }
}

