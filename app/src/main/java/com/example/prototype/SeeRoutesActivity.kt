package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.adapters.RoutesAdapter

class SeeRoutesActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeroutes)
        mRecyclerView = findViewById(R.id.recyclerViewRoutes)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = RoutesAdapter()
    }
}
