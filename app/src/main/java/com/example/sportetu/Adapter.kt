package com.example.sportetu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.entrainement_activity_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //Number of items
    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}