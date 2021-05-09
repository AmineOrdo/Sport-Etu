package com.example.sportetu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportetu.R

class AdapterSucces (private var titre: List<String>, private var description:List<String>,private var image:List<Int>):
        RecyclerView.Adapter<AdapterSucces.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val itemTitre: TextView = itemView.findViewById(R.id.nomSucces)
        val itemDescription: TextView = itemView.findViewById(R.id.description_succes)
        val itemImage: ImageView = itemView.findViewById(R.id.imageSucces)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.container_succes,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitre.text=titre[position]
        holder.itemDescription.text=description[position]
        holder.itemImage.setImageResource(image[position])

    }

    override fun getItemCount(): Int {
        return titre.size
    }
}

