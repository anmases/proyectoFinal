package com.example.proyectofinal.repository

import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.models.Tren
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiClient {
    /**
     * Métodos Get
     */
    @GET("api/tren")
    fun getTrenes(): Call<ArrayList<Tren>>
    @GET("api/estacion")
    fun getEstaciones(): Call<ArrayList<Estacion>>
    @GET("api/empleado")
    fun getEmpleados(): Call<ArrayList<Empleado>>

    /**
     * Métodos Post
     */
    @POST("api/tren")
    fun postTren(@Body tren: Tren): Call<ApiResponse>
    @POST("api/estacion")
    fun postEstacion(@Body estacion: Estacion): Call<ApiResponse>
    @POST("api/empleado")
    fun postEmpleado(@Body empleado: Empleado): Call<ApiResponse>

    /**
     * Métodos delete
     */
    @DELETE("api/tren")
    fun deleteTren(@Query("id") id:Int): Call<ApiResponse>
    @DELETE("api/estacion")
    fun deleteEstacion(@Query("id") id:Int): Call<ApiResponse>
    @DELETE("api/empleado")
    fun deleteEmpleado(@Query("id") id:Int): Call<ApiResponse>

    /**
     * Métodos put
     */
    @PUT("api/tren")
    fun updateTren(@Query("id") id: Int, @Body fields: JsonObject): Call<ApiResponse>
    @PUT("api/estacion")
    fun updateEstacion(@Query("id") id: Int, @Body fields: JsonObject): Call<ApiResponse>
    @PUT("api/empleado")
    fun updateEmpleado(@Query("id") id: Int, @Body fields: JsonObject): Call<ApiResponse>
}