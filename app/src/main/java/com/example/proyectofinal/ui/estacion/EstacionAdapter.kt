package com.example.proyectofinal.ui.estacion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.ui.estacion.EstacionViewHolder

class EstacionAdapter (var listaEstaciones: ArrayList<Estacion>, val eventListener: (Estacion, View)->Unit, val deleteListener: (Estacion)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vista = inflater.inflate(R.layout.estacion, parent, false)
        return EstacionViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaEstaciones.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val estacionViewHolder = holder as EstacionViewHolder
        estacionViewHolder.bind(listaEstaciones[position], eventListener, deleteListener)
    }
}