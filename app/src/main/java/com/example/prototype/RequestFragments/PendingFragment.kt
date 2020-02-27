package com.example.prototype.RequestFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.PackageAdapter
import com.example.prototype.dataModels.ReviewInformationDataModel

class PendingFragment : Fragment() {
    private lateinit var root: View
    private lateinit var mRecyclerView: RecyclerView
//    private lateinit var packageProgressBar: ProgressBar
    private lateinit var shimmerRecyclerView: ShimmerRecyclerView
    private lateinit var noPending:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_request_active, container, false)
        shimmerRecyclerView = root.findViewById(R.id.active_shimmer_recycler_view)
        noPending = root.findViewById(R.id.textViewActive)
        loadRequests()
        return root
    }

    override fun onResume() {
        loadRequests()
        super.onResume()
    }

    fun loadRequests() {
//        packageProgressBar = root.findViewById(R.id.requestProgressBar)
//        packageProgressBar.visibility = View.VISIBLE
        shimmerRecyclerView.visibility = View.VISIBLE
        Util.getFirebaseFireStore().collection("carRideRequests")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .whereIn(
                "requestStatus",
                arrayListOf("Pending", "Payment Pending", "Payment Verification")
            )
            .get()
            .addOnSuccessListener {
                if (it.documents.size == 0) {
                    noPending.visibility = View.VISIBLE
                    noPending.text = "No Pending Request Found"
                    shimmerRecyclerView.visibility = View.INVISIBLE
                } else {
                    val requests = mutableListOf<ReviewInformationDataModel>()
                    for (documents in it.documents) {
                        val info = documents.toObject(ReviewInformationDataModel::class.java)
                        info!!.requestID = documents.id
                        requests.add(info!!)
                    }
                    mRecyclerView = root.findViewById(R.id.requestRecyclerView)
                    mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                    mRecyclerView.adapter =
                        PackageAdapter(requests, root.context, shimmerRecyclerView)
//                packageProgressBar.visibility = View.INVISIBLE
                    shimmerRecyclerView.visibility = View.INVISIBLE
                }
            }
    }
}