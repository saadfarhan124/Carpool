package com.example.prototype.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.DespositSlipUploadActivity
import com.example.prototype.R
import com.example.prototype.ReviewInformationActivity
import com.example.prototype.UpdateDaysAndTime
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.DaysDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.google.android.material.chip.Chip
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick


class PackageAdapter(
    private val requestsDataModel: MutableList<ReviewInformationDataModel>,
    private val context: Context,
    private val packageProgressBar: ProgressBar
) : RecyclerView.Adapter<PackageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_package_row, parent, false)
        return PackageViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return requestsDataModel.size
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.chipMon.enabled = false
        holder.chipTue.enabled = false
        holder.chipWed.enabled = false
        holder.chipThu.enabled = false
        holder.chipFri.enabled = false
        holder.chipSat.enabled = false
        holder.chipSun.enabled = false

        val request = requestsDataModel[position]
        Util.getFirebaseFireStore().collection("carRideRequests")
            .document(request.requestID.toString())
            .get().addOnSuccessListener { requestTask ->
                requestTask.reference.collection("Days")
                    .get()
                    .addOnSuccessListener {
                        for (document in it.documents) {
                            Log.d("Saad", document.data.toString())
                            if (document.get("day").toString() == "Monday") {
                                holder.chipMon.enabled = document.get("day").toString() == "Monday"
                                holder.chipMon.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Tuesday") {
                                holder.chipTue.enabled = document.get("day").toString() == "Tuesday"
                                holder.chipTue.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Wednesday") {
                                holder.chipWed.enabled =
                                    document.get("day").toString() == "Wednesday"
                                holder.chipWed.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Thursday") {
                                holder.chipThu.enabled =
                                    document.get("day").toString() == "Thursday"
                                holder.chipThu.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Friday") {
                                holder.chipFri.enabled = document.get("day").toString() == "Friday"
                                holder.chipFri.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Saturday") {
                                holder.chipSat.enabled = document.get("day").toString() == "Saturday"
                                holder.chipSat.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else if (document.get("day").toString() == "Sunday") {
                                holder.chipSun.enabled = document.get("day").toString() == "Sunday"
                                holder.chipSun.onClick {
                                    Toast.makeText(
                                        context,
                                        "Pick up time ${document.get("pickUpTime")} \n " +
                                                "Drop off time ${document.get("dropOffTime")}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }
            }

        holder.txt_pickupAddress.text = request!!.pickUpAddress
        holder.txt_dropoffAddress.text = request!!.dropOffAddress
        holder.textViewStatus.text = request!!.requestStatus


        if (request!!.requestStatus == ("Pending")) {
            holder.btnPay.onClick {
                Toast.makeText(context, "Payment not enabled yet", Toast.LENGTH_LONG).show()
            }
            holder.btnChange.onClick {
                var intent = Intent(context, UpdateDaysAndTime::class.java)
                intent.putExtra("requestID", request.requestID)
                context.startActivity(intent)
            }
        } else if(request!!.requestStatus == ("Payment Pending")) {
            holder.btnPay.onClick {
                var intent = Intent(context, DespositSlipUploadActivity::class.java)
                intent.putExtra("requestID", request.requestID)
                context.startActivity(intent)
            }
            holder.btnChange.onClick {
                var intent = Intent(context, UpdateDaysAndTime::class.java)
                intent.putExtra("requestID", request.requestID)
                context.startActivity(intent)
            }
        } else if(request!!.requestStatus == ("Payment Processing")){
            holder.btnPay.onClick {
                Toast.makeText(context, "Payment already being processed", Toast.LENGTH_SHORT).show()
            }
        }



        holder.btnCancel.onClick {
            packageProgressBar.visibility = View.VISIBLE
            val alertDialog = Util.getAlertDialog(context)
            alertDialog.setMessage("Do you really want to cancel this request?")
            alertDialog.setPositiveButton("Yes") { _, _ ->
                Util.getFirebaseFireStore().collection("carRideRequests")
                    .document(request.requestID.toString())
                    .update("requestStatus", "Cancelled")
                    .addOnSuccessListener {
                        holder.textViewStatus.text = "Cancelled"
                        Toast.makeText(context, "Request successfully cancelled", Toast.LENGTH_LONG)
                            .show()
                        packageProgressBar.visibility = View.INVISIBLE
                    }
            }
            alertDialog.setNegativeButton("No") { _, _ -> }
            alertDialog.show()
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