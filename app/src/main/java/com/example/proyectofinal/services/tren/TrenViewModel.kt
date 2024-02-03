package com.example.proyectofinal.services.tren

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.repository.Repository
import com.example.proyectofinal.models.Tren
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.ui.tren.TrenAdapter
import com.google.gson.JsonObject

class TrenViewModel : ViewModel() {
//Cuando cambia avisa a todos sus observers para que cambien su valor. Se observa el adapter
    private val _response = MutableLiveData<ApiResponse>()
    private val _selectedTren: MutableLiveData<Tren> by lazy {
        MutableLiveData<Tren>()
    }
    private val _adapter = MutableLiveData<TrenAdapter>().apply {
        Repository.listarTrenes {
            value = TrenAdapter(it,
                {tren: Tren, view: View -> trenOnClick(tren, view)},
                {tren ->deleteOnClick(tren)}
            )
        }
    }

    val adapter: LiveData<TrenAdapter> = _adapter
    val response: LiveData<ApiResponse> = _response
    val selectedTren: LiveData<Tren> = _selectedTren

    fun setSelectedTren(tren: Tren){
        _selectedTren.value = tren
    }
    fun refresh(){
        Repository.listarTrenes {
            _adapter.value = TrenAdapter(it,
                {tren: Tren, view: View -> trenOnClick(tren, view)},
                {tren ->deleteOnClick(tren)}
            )
        }
    }
    fun actualizarTren(tren: Tren){
        //Según la lógica de nuestra API, se debe pasar como parámetro el ID y solo los campos que hayan cambiado:
        val fields = JsonObject()
        if(!tren.modelo.equals(_selectedTren.value?.modelo)) fields.addProperty("modelo",tren.modelo )
        if(tren.capacidad != _selectedTren.value?.capacidad) fields.addProperty("capacidad", tren.capacidad)
        Repository.actualizarTren(tren.id!!, fields){
            _response.value = it
        }
    }
    fun insertarTren(tren: Tren){
        Repository.crearTren(tren){
            _response.value = it
        }
    }

    fun deleteOnClick(tren: Tren){
        Repository.eliminarTren(tren){
            _response.value = it
            if(it.result == "ok") refresh()
        }
    }

    fun trenOnClick(tren: Tren, view: View){
        _selectedTren.value = tren
    }

}
