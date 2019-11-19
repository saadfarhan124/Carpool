package com.example.prototype.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R

class MyridesAdapter: RecyclerView.Adapter<MyridesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyridesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_myrides_row, parent, false)
        return MyridesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: MyridesViewHolder, position: Int) {

    }
}
class MyridesViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}