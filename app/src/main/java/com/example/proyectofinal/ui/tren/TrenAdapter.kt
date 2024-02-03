package com.example.proyectofinal.ui.tren

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Tren
import com.example.proyectofinal.ui.tren.TrenViewHolder

class TrenAdapter (var lista: List<Tren>, val eventListener: (Tren, View)->Unit, val deleteListener: (Tren)->Unit):  RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vista = inflater.inflate(R.layout.tren, parent, false)
        return TrenViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, index: Int) {
        (holder as TrenViewHolder).bind(lista[index], eventListener, deleteListener)  //Se llama al callback
    }

}