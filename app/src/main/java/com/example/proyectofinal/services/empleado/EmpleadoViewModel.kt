package com.example.proyectofinal.services.empleado

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.repository.Repository
import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.ui.empleado.EmpleadoAdapter

class EmpleadoViewModel : ViewModel() {
    private val _response = MutableLiveData<ApiResponse>()
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

    fun insertarEmpleado(empleado: Empleado){
        Repository.crearEmpleado(empleado){ response ->
            _response.value = response
        }
    }



    fun empleadoOnClick(empleado: Empleado, view: View){

    }
}