package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.prototype.adapters.RoutesAdapter
import com.example.prototype.dataModels.Routes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class SeeRoutesActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var db:FirebaseFirestore
    private var mAdapter: RoutesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeroutes)
        db = FirebaseFirestore.getInstance()
        loadRoutes()


    }

    private fun loadRoutes(){
        db.collection("routes")
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val routes = mutableListOf<Routes>()
                    for(document in task.result!!){
                        val route = document.toObject(Routes::class.java)
                        route.startingTime = document["starting_time"].toString()
                        route.startingPoint = document["starting_point"].toString()
                        route.endingTime = document["ending_time"].toString()
                        route.endingPoint = document["ending_point"].toString()
                        route.remainingSeats = document["seats_remaining"] as Long
                        route.id = document.id
                        routes.add(route)
                    }
                    mAdapter = RoutesAdapter(routes, applicationContext, db!!)
                    mRecyclerView = findViewById(R.id.recyclerViewRoutes)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    mRecyclerView.layoutManager = mLayoutManager
                    mRecyclerView.itemAnimator = DefaultItemAnimator()
                    mRecyclerView.adapter = mAdapter
                }else{
                    Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
}
