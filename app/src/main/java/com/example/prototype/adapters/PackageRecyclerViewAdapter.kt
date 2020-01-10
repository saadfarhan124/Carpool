package com.example.prototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.RequestsDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.example.prototype.dataModels.Routes
import com.google.android.material.chip.Chip


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
        val request = requestsDataModel[position]

        holder.txt_pickupAddress.text = request.reviewInformationDataModel!!.pickUpAddress

    }
}
class PackageViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var chipMon: Chip = v.findViewById(R.id.chipMon)
    internal var chipTue: Chip = v.findViewById(R.id.chipTue)
    internal var chipWed: Chip = v.findViewById(R.id.chipWed)
    internal var chipThu: Chip = v.findViewById(R.id.chipThu)
    internal var chipFri: Chip = v.findViewById(R.id.chipFri)
    internal var chipSat: Chip = v.findViewById(R.id.chipSat)
    internal var chipSun: Chip = v.findViewById(R.id.chipSun)
    internal var txt_pickupAddress: TextView = v.findViewById(R.id.txt_pickupAddress)
    internal var txt_dropoffAddress: TextView = v.findViewById(R.id.txt_dropoffAddress)
    internal var textViewStatus: TextView = v.findViewById(R.id.textViewStatus)
    internal var textViewAmount: TextView = v.findViewById(R.id.txt_dropoffAddress)
    internal var btnChange: Button = v.findViewById(R.id.btnChange)
    internal var btnCancel: Button = v.findViewById(R.id.btnCancel)

}