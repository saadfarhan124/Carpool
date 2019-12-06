package com.example.prototype.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.BookingActivity
import com.example.prototype.R
import com.example.prototype.dataModels.Routes
import com.google.firebase.firestore.FirebaseFirestore
import java.math.RoundingMode
import kotlin.math.roundToLong

class RoutesAdapter(
    private val routesList: MutableList<Routes>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore
) : RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.activity_routesitem, parent, false)
        return RoutesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return routesList.size
    }

    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
        val route = routesList[position]
        holder.startingPoint.text = route!!.startingPoint
        holder.startingTime.text = route!!.startingTime
        holder.endingTime.text = route!!.endingTime
        holder.endingPoint.text = route!!.endingPoint
        holder.seatsRemaining.text = route!!.seatsRemaining.toString()
        holder.distanceFromPickup.text = "${String.format("%.2f",route!!.distanceFromPickUp)} M from pick up point"
        holder.distanceFromDest.text =  "${String.format("%.2f",route!!.distanceFromDropOff)} M from pick up point"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, BookingActivity::class.java)
            intent.putExtra("routeDetails", route)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }


    inner class RoutesViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var startingPoint: TextView = view.findViewById(R.id.startingPoint)
        internal var startingTime: TextView = view.findViewById(R.id.startingTime)
        internal var endingPoint: TextView = view.findViewById(R.id.endingPoint)
        internal var endingTime: TextView = view.findViewById(R.id.endingTime)
        internal var seatsRemaining: TextView = view.findViewById(R.id.seatsRemaining)
        internal var distanceFromPickup: TextView = view.findViewById(R.id.distanceFromPickUp)
        internal var distanceFromDest: TextView = view.findViewById(R.id.distanceFromDropOff)
    }

}

