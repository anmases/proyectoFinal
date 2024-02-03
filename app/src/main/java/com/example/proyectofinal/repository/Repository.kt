package com.example.proyectofinal.repository

import android.util.Log
import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.models.Tren
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class Repository {
    companion object{
        private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2").addConverterFactory(GsonConverterFactory.create()).build()
        val userApi = retrofit.create<ApiClient>()

/***********************************LISTAR******************************************************/

        fun listarTrenes(listener: (ArrayList<Tren>) -> Unit) {
            val call = userApi.getTrenes()
            return call.enqueue(object : Callback<ArrayList<Tren>> {
                override fun onResponse(call: Call<ArrayList<Tren>>, response: Response<ArrayList<Tren>>) {
                    if (response.isSuccessful) {
                        Log.d("RESPONSE", "${response.body()!!.size}")
                        if (response.body() != null) listener(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Tren>>, t: Throwable) {
                    Log.d("API_ERROR", "Error en onFailure: ${t.message}")
                }
            })
        }
        fun listarEstaciones(listener: (ArrayList<Estacion>)->Unit){
            var call = userApi.getEstaciones()
            return call.enqueue(object: Callback<ArrayList<Estacion>> {
                override fun onResponse(call: Call<ArrayList<Estacion>>, response: Response<ArrayList<Estacion>>) {
                    if(response.isSuccessful && response.body() != null){
                        listener(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Estacion>>, t: Throwable) {
                    Log.d("API_ERROR", "Error en onFailure: ${t.message}")
                }

            })
        }
        fun listarEmpleados(listener: (ArrayList<Empleado>)->Unit){
            var call = userApi.getEmpleados()
            return call.enqueue(object: Callback<ArrayList<Empleado>> {
                override fun onResponse(call: Call<ArrayList<Empleado>>, response: Response<ArrayList<Empleado>>) {
                    if(response.isSuccessful && response.body() != null){
                        listener(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Empleado>>, t: Throwable) {
                    Log.d("API_ERROR", "Error en onFailure: ${t.message}")
                }

            })
        }
/****************************************INSERTAR************************************************/

        fun crearTren(tren: Tren, listener: (ApiResponse)->Unit){
            val call = userApi.postTren(tren)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun crearEstacion(estacion: Estacion, listener:(ApiResponse)->Unit){
            val call = userApi.postEstacion(estacion)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun crearEmpleado(empleado: Empleado, listener: (ApiResponse) -> Unit){
            val call = userApi.postEmpleado(empleado)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
/*******************************************ELIMINAR*************************************************/
        fun eliminarTren(tren: Tren, listener: (ApiResponse) -> Unit){
            val call = userApi.deleteTren(tren.id?:0)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun eliminarEstacion(estacion: Estacion, listener: (ApiResponse) -> Unit){
            val call = userApi.deleteEstacion(estacion.id?:0)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun eliminarEmpleado(empleado: Empleado, listener: (ApiResponse) -> Unit){
            val call = userApi.deleteEmpleado(empleado.id?:0)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
/*******************************************ACTUALIZAR*******************************************/
        fun actualizarTren(id: Int, fields: JsonObject, listener: (ApiResponse)->Unit){
            val call = userApi.updateTren(id, fields)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun actualizarEstacion(id: Int, fields: JsonObject, listener: (ApiResponse)->Unit){
            val call = userApi.updateEstacion(id, fields)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
        fun actualizarEmpleado(id: Int, fields: JsonObject, listener: (ApiResponse)->Unit){
            val call = userApi.updateEmpleado(id, fields)
            call.enqueue(object: Callback<ApiResponse>{
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    val body = response.body()
                    if(response.isSuccessful && body?.result == "ok"){
                        listener(ApiResponse(body.result))
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.d("API ERROR", "Error")
                }
            })
        }
    }
}