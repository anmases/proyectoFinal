package com.example.proyectofinal.ui.empleado

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.ui.empleado.EmpleadoViewHolder

class EmpleadoAdapter(var lista: ArrayList<Empleado>, val eventListener: (Empleado, View)->Unit, val deleteListener: (Empleado)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vista = inflater.inflate(R.layout.empleado, parent, false)
        return EmpleadoViewHolder(vista)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val empleadoViewHolder = holder as EmpleadoViewHolder
        empleadoViewHolder.bind(lista[position], eventListener, deleteListener)
    }
}