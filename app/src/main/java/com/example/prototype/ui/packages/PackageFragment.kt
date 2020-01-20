package com.example.prototype.ui.packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.prototype.R
import com.example.prototype.adapters.RequestSectionPageAdapter

import com.google.android.material.tabs.TabLayout

class PackageFragment : Fragment() {
    //    private lateinit var packageViewModel:PackageViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var packageProgressBar: ProgressBar

    private lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_package, container, false)

        val requestSectionPageAdapter = RequestSectionPageAdapter(root.context,childFragmentManager)
        val viewPager: ViewPager = root.findViewById(R.id.view_pagerrequest)
        viewPager.adapter = requestSectionPageAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)

//        loadRequests()
        return root
    }

//    override fun onResume() {
//        loadRequests()
//        super.onResume()
//    }
//
//    fun loadRequests() {
//        packageProgressBar = root.findViewById(R.id.packageProgressBar)
//        packageProgressBar.visibility = View.VISIBLE
//        Util.getFirebaseFireStore().collection("carRideRequests")
//            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
//            .get()
//            .addOnSuccessListener {
//                val requests = mutableListOf<ReviewInformationDataModel>()
//                for (documents in it.documents) {
//
//
//
//                    val info = documents.toObject(ReviewInformationDataModel::class.java)
//                    info!!.requestID = documents.id
//                    requests.add(info!!)
//                }
//                mRecyclerView = root.findViewById(R.id.packageRecyclerView)
//                mRecyclerView.layoutManager = LinearLayoutManager(root.context)
//                mRecyclerView.adapter = PackageAdapter(requests, root.context, packageProgressBar)
//                packageProgressBar.visibility = View.INVISIBLE
//            }
//    }
}