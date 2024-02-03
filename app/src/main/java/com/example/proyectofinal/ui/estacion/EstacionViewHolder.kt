package com.example.proyectofinal.ui.estacion

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Estacion

class EstacionViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val nombre = itemView.findViewById<TextView>(R.id.estacionNombre)
    val ciudad = itemView.findViewById<TextView>(R.id.estacionCiudad)
    val eliminar = itemView.findViewById<ImageButton>(R.id.deleteEstacion)
    fun bind(estacion: Estacion, eventListener: (Estacion, View )-> Unit, deleteListener: (Estacion)->Unit){
        nombre.text = estacion.nombre
        ciudad.text = estacion.ciudad
        eliminar.setOnClickListener{deleteListener(estacion)}
        itemView.setOnClickListener { eventListener(estacion, itemView) }
    }
}