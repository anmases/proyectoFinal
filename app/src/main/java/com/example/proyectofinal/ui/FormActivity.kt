package com.example.proyectofinal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyectofinal.databinding.ActivityFormBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.models.Tren


class FormActivity: AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val formType = intent.getStringExtra("FORM_TYPE")

        setSupportActionBar(binding.appBarForm.toolbar)
        val navController = findNavController(R.id.nav_host_form_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        val actionId = when (formType) {
            "TREN" -> R.id.trenFormFragment
            "ESTACION" -> R.id.estacionFormFragment
            "EMPLEADO" -> R.id.empleadoFormFragment
            else -> throw IllegalArgumentException("Form type not recognized")
        }
        navController.navigate(actionId)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_form_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}