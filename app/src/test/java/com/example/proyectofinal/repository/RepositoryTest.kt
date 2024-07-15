package com.example.proyectofinal.repository

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiClient: ApiClient

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }

    @Test
    fun `listarTrenes returns list successfully`() {
        // Configura MockWebServer para responder a la solicitud
        val jsonResponse = ""
            mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // Realiza la llamada y verifica el resultado
        Repository.listarTrenes { trenes ->
            // Verifica que la lista de trenes no esté vacía o lo que sea relevante
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}