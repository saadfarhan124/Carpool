package com.example.prototype.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.TransactionAdapter
import com.example.prototype.dataModels.InvoiceDataModel

class TransactionFragment : Fragment() {

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var root:View
    private lateinit var listOfInvoices: MutableList<InvoiceDataModel>
    private lateinit var transactionProgressBar: ProgressBar
    private lateinit var shimmerRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionViewModel =
            ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_transaction, container, false)
//        transactionProgressBar = root.findViewById(R.id.transactionProgressBar)
        shimmerRecyclerView = root.findViewById(R.id.transaction_shimmer_recycler_view)
        loadInvoices()

        return root
    }

    private fun loadInvoices(){
//        transactionProgressBar.visibility = View.VISIBLE
        shimmerRecyclerView.visibility = View.VISIBLE
        listOfInvoices = mutableListOf()
        Util.getFirebaseFireStore().collection("invoices")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .get()
            .addOnSuccessListener {
                if(it.documents.size == 0){
                    Toast.makeText(root.context, "No Invoices found", Toast.LENGTH_SHORT).show()
                }else{
                    for(document in it.documents){
                        val invoice = InvoiceDataModel()
                        invoice.date = document["date"].toString()
                        invoice.invoiceAmount = document["invoiceAmount"].toString().toInt()
                        listOfInvoices.add(invoice!!)
                    }
                    mRecyclerView = root.findViewById(R.id.transactionRecyclerView)
                    mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                    mRecyclerView.adapter = TransactionAdapter(listOfInvoices)
                }

//                transactionProgressBar.visibility = View.INVISIBLE
                shimmerRecyclerView.visibility = View.INVISIBLE
            }
    }

}