package com.example.proyectofinal.services.empleado

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.repository.Repository
import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.ui.empleado.EmpleadoAdapter
import com.google.gson.JsonObject

class EmpleadoViewModel : ViewModel() {
    private val _response = MutableLiveData<ApiResponse>()
    private val _selectedEmpleado: MutableLiveData<Empleado> by lazy {
        MutableLiveData<Empleado>()
    }
    private val _adapter = MutableLiveData<EmpleadoAdapter>().apply {
        Repository.listarEmpleados {
            value = EmpleadoAdapter(it,
                {empleado: Empleado, view: View-> empleadoOnClick(empleado, view)},
                {empleado: Empleado -> deleteOnClick(empleado) }
            )
        }
    }
    val adapter: LiveData<EmpleadoAdapter> = _adapter
    val response: LiveData<ApiResponse> = _response
    val selectedEmpleado: LiveData<Empleado> = _selectedEmpleado

    fun setSelectedEmpleado(empleado: Empleado){
        _selectedEmpleado.value = empleado
    }
    fun refresh(){
        Repository.listarEmpleados {
            _adapter.value = EmpleadoAdapter(it,
                {empleado, view -> empleadoOnClick(empleado, view)},
                {empleado: Empleado -> deleteOnClick(empleado) }
            )
        }
    }

    fun deleteOnClick(empleado: Empleado){
        Repository.eliminarEmpleado(empleado){
            _response.value = it
            if(it.result == "ok") refresh()
        }
    }

    fun actualizarEmpleado(empleado: Empleado){
        //Según la lógica de nuestra API, se debe pasar como parámetro el ID y solo los campos que hayan cambiado:
        val fields = JsonObject()
        if(!empleado.contratado.equals(_selectedEmpleado.value?.contratado)) fields.addProperty("contratado",empleado.contratado )
        if(!empleado.nombre.equals(_selectedEmpleado.value?.nombre)) fields.addProperty("nombre",empleado.nombre )
        if(!empleado.puesto.equals(_selectedEmpleado.value?.puesto)) fields.addProperty("puesto",empleado.puesto )
        if(!empleado.estacion_id.equals(_selectedEmpleado.value?.estacion_id)) fields.addProperty("estacion_id",empleado.estacion_id )
        Repository.actualizarEmpleado(empleado.id!!, fields){
            _response.value = it
        }
    }
    fun insertarEmpleado(empleado: Empleado){
        Repository.crearEmpleado(empleado){ response ->
            _response.value = response
        }
    }

    fun empleadoOnClick(empleado: Empleado, view: View){
        _selectedEmpleado.value = empleado
    }
}