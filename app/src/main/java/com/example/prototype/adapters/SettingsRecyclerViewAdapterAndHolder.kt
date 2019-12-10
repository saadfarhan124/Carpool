package com.example.prototype.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import kotlinx.android.synthetic.main.activity_rc_row.view.*
import org.jetbrains.anko.image

class SettingsAdapter : RecyclerView.Adapter<SettingsViewHolder>()  {

    private var firstSectionTitles = listOf("Name", "Mobile Number", "Email", "Change Password", "Gender", "Date of Birth")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var cellForRow = layoutInflater.inflate(R.layout.activity_rc_row, parent, false)

        return SettingsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.itemView.textViewSettings.text = firstSectionTitles[position]
        holder.itemView.setOnClickListener{
            Toast.makeText(it.context, position.toString(), Toast.LENGTH_LONG).show()
        }

    }

    override fun getItemCount(): Int {
        return firstSectionTitles.size
    }
}

class SettingsViewHolder(v: View): RecyclerView.ViewHolder(v){

}