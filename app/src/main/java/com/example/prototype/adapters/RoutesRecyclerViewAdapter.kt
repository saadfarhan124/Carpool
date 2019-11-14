package com.example.prototype.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R

class RoutesAdapter : RecyclerView.Adapter<RoutesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_routesitem, parent, false)
        return RoutesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {

    }

}

class RoutesViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}