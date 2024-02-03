package com.example.proyectofinal.ui.empleado

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Empleado

class EmpleadoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    //declaramos los componentes de la celda:
    val contratado = itemView.findViewById<TextView>(R.id.empleadoContratado)
    val nombre = itemView.findViewById<TextView>(R.id.empleadoNombre)
    val puesto = itemView.findViewById<TextView>(R.id.empleadoPuesto)
    val estacion = itemView.findViewById<TextView>(R.id.empleadoEstacion)
    val eliminar = itemView.findViewById<ImageButton>(R.id.deleteEmpleado)

    fun bind(empleado: Empleado, eventListener: (Empleado, View)->Unit, deleteListener: (Empleado)->Unit){
        contratado.text = empleado.contratado
        nombre.text = empleado.nombre
        puesto.text = empleado.puesto
        estacion.text = empleado.estacion_id.toString()
        eliminar.setOnClickListener { deleteListener(empleado) }
        itemView.setOnClickListener{ eventListener(empleado, itemView) }
    }
}