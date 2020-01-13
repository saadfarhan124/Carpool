package com.example.prototype.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.ReviewInformationActivity
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.google.android.material.chip.Chip
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick


class PackageAdapter(private val requestsDataModel: List<ReviewInformationDataModel>,
                     private val context: Context,
                     private val packageProgressBar: ProgressBar): RecyclerView.Adapter<PackageViewHolder>() {

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


        holder.txt_pickupAddress.text = request!!.pickUpAddress
        holder.txt_dropoffAddress.text = request!!.dropOffAddress
        holder.textViewStatus.text = request!!.requestStatus

        if(request!!.requestStatus == ("Pending")){
            holder.btnPay.onClick {
                Toast.makeText(context, "Payment not enabled yet", Toast.LENGTH_LONG).show()
            }
        }else{
            holder.btnPay.onClick {

            }
        }

        holder.btnChange.onClick {

        }

        holder.btnCancel.onClick {
            packageProgressBar.visibility = View.VISIBLE
        }



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
    internal var btnPay: Button = v.findViewById(R.id.btnPay)

}