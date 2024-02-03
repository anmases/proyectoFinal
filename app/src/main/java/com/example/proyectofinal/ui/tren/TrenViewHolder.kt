package com.example.proyectofinal.ui.tren

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Tren

class TrenViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val modelo = itemView.findViewById<TextView>(R.id.trenModelo)
    val capacidad = itemView.findViewById<TextView>(R.id.trenCapacidad)
    val eliminar = itemView.findViewById<ImageButton>(R.id.deleteTren)

    fun bind(tren: Tren, eventListener: (Tren, View)-> Unit, deleteListener: (Tren)->Unit){
        modelo.text = tren.modelo
        capacidad.text = tren.capacidad.toString()
        eliminar.setOnClickListener{deleteListener(tren)}
        itemView.setOnClickListener{eventListener(tren, itemView)}
    }

}