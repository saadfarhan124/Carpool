package com.example.prototype.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.NotificationAdapter
import com.example.prototype.dataModels.NotificationDataModel


class NotificationFragment:Fragment() {
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var root:View
    private lateinit var listOfNotification: MutableList<NotificationDataModel>
    private  lateinit var shimmerRecyclerView: ShimmerRecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationViewModel =
            ViewModelProviders.of(this).get(NotificationViewModel::class.java)
         root = inflater.inflate(R.layout.fragment_notificaiton, container, false)

        shimmerRecyclerView = root.findViewById(R.id.shimmer_recycler_view)


        loadNotification()
        return root
    }

    private fun loadNotification(){

        shimmerRecyclerView.visibility = View.VISIBLE
        listOfNotification = mutableListOf()
        Util.getFirebaseFireStore().collection("notification")
            .whereEqualTo("userID", Util.getGlobals().user!!.uid)
            .get()
            .addOnSuccessListener {

                    for(document in it.documents){

                        val notification = document.toObject(NotificationDataModel::class.java)
                        listOfNotification.add(notification!!)
                    }
                    mRecyclerView = root.findViewById(R.id.notificationRecyclerView)
                    mRecyclerView.layoutManager = LinearLayoutManager(root.context)
                    mRecyclerView.adapter = NotificationAdapter(listOfNotification)

                shimmerRecyclerView.visibility = View.INVISIBLE

            }
    }
}

