package com.example.prototype.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.dataModels.InvoiceDataModel


class TransactionAdapter(private val invoicesList: MutableList<InvoiceDataModel>) :
    RecyclerView.Adapter<TransactionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_transaction_row, parent, false)
        return TransactionViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return invoicesList.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val invoice = invoicesList[position]
        holder.textViewDate.text = invoice.date
//        holder.textViewtInvoiceNumber.text = invoice.invoiceNumber
        holder.textViewAmount.text = invoice.invoiceAmount.toString()
    }
}

class TransactionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    internal var textViewtInvoiceNumber: TextView = v.findViewById(R.id.textViewtInvoiceNumber)
    internal var textViewAmount: TextView = v.findViewById(R.id.textViewAmount)
    internal var textViewDate: TextView = v.findViewById(R.id.textViewDate)

}