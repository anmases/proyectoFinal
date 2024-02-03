package com.example.proyectofinal.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_tren, R.id.nav_estacion, R.id.nav_empleado
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    //Acción del botón añadir:
        binding.appBarMain.addButton.setOnClickListener { view ->
           when(navController.currentDestination?.id){
                R.id.nav_tren ->{
                    val intent = Intent(this@MainActivity, FormActivity::class.java).apply {
                        putExtra("FORM_TYPE", "TREN")
                    }
                    startActivity(intent)
                }
                R.id.nav_estacion -> {
                    val intent = Intent(this@MainActivity, FormActivity::class.java).apply {
                        putExtra("FORM_TYPE", "ESTACION")
                    }
                    startActivity(intent)
                }
                R.id.nav_empleado -> {
                    val intent = Intent(this@MainActivity, FormActivity::class.java).apply {
                        putExtra("FORM_TYPE", "EMPLEADO")
                    }
                    startActivity(intent)
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}