package com.example.proyectofinal.services.estacion

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.repository.Repository
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.models.Tren
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.ui.estacion.EstacionAdapter
import com.google.gson.JsonObject

class EstacionViewModel : ViewModel() {
    private val _response = MutableLiveData<ApiResponse>()
    private val _selectedEstacion: MutableLiveData<Estacion> by lazy {
        MutableLiveData<Estacion>()
    }
    private val _estaciones = MutableLiveData<List<Estacion>>().apply {
        Repository.listarEstaciones { estaciones ->
            value = estaciones
        }
    }
    private val _adapter = MutableLiveData<EstacionAdapter>().apply {
        //Conecta con el repositorio y obtiene los datos.
        Repository.listarEstaciones {
            value = EstacionAdapter(it,
                {estacion: Estacion, view: View -> estacionOnClick(estacion, view)},
                {estacion: Estacion ->  deleteOnClick(estacion)}
            )
        }
    }
    val adapter: LiveData<EstacionAdapter> = _adapter
    val response: LiveData<ApiResponse> = _response
    val estaciones: LiveData<List<Estacion>> = _estaciones
    val selectedEstacion: LiveData<Estacion> = _selectedEstacion


    fun setSelectedEstacion(estacion: Estacion){
        _selectedEstacion.value = estacion
    }
    fun refresh(){
        Repository.listarEstaciones {
            _adapter.value = EstacionAdapter(it,
                {estacion, view ->  estacionOnClick(estacion, view)},
                {estacion: Estacion -> deleteOnClick(estacion) }
            )
        }
    }
    fun actualizarEstacion(estacion: Estacion){
        //Según la lógica de nuestra API, se debe pasar como parámetro el ID y solo los campos que hayan cambiado:
        val fields = JsonObject()
        if(!estacion.nombre.equals(_selectedEstacion.value?.nombre)) fields.addProperty("nombre",estacion.nombre )
        if(!estacion.ciudad.equals(_selectedEstacion.value?.ciudad)) fields.addProperty("ciudad",estacion.ciudad )
        Repository.actualizarEstacion(estacion.id!!, fields){
            _response.value = it
        }
    }
    fun deleteOnClick(estacion: Estacion){
        Repository.eliminarEstacion(estacion){
            _response.value = it
            if(it.result == "ok") refresh()
        }
    }

    fun insertarEstacion(estacion: Estacion){
        Repository.crearEstacion(estacion){ response ->
            _response.value = response
        }
    }

    fun estacionOnClick(estacion: Estacion, view: View){
        _selectedEstacion.value = estacion
    }
}
