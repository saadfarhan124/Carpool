package com.example.prototype.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R


class MyshareRidesAdapter: RecyclerView.Adapter<MyshareRidesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyshareRidesViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = LayoutInflater.inflate(R.layout.activity_myrides_share_row, parent, false)
        return MyshareRidesViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: MyshareRidesViewHolder, position: Int) {

    }
}
class MyshareRidesViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}