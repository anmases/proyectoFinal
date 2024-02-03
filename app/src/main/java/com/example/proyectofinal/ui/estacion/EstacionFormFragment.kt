package com.example.proyectofinal.ui.estacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.databinding.FragmentEstacionFormBinding
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.models.Tren
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.services.estacion.EstacionViewModel



class EstacionFormFragment : Fragment() {
    private var _binding: FragmentEstacionFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //infla la vista del fragment.
        _binding = FragmentEstacionFormBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val estacionViewModel = ViewModelProvider(this).get(EstacionViewModel::class.java)
        var id: Int? = null
        val ciudad = binding.textEstacionCiudad.text
        val nombre = binding.textEstacionNombre.text

        //Recuperamos los datos del intent
        if(
            requireActivity().intent.hasExtra("ID") &&
            requireActivity().intent.hasExtra("NAME") &&
            requireActivity().intent.hasExtra("CITY")
        )
        {
            id = requireActivity().intent.getIntExtra("ID", -1)
            nombre.append(requireActivity().intent.getStringExtra("NAME"), "")
            ciudad.append(requireActivity().intent.getStringExtra("CITY"), "")
            estacionViewModel.setSelectedEstacion(
                Estacion(
                    requireActivity().intent.getIntExtra("ID", 0),
                    requireActivity().intent.getStringExtra("NAME")!!,
                    requireActivity().intent.getStringExtra("CITY")!!
                )
            )
        }


        estacionViewModel.response.observe(viewLifecycleOwner){ response: ApiResponse ->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Insertado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }

        binding.submitEstacionButton.setOnClickListener {
            id?.let {
                val item = Estacion(id, nombre.toString(), ciudad.toString())
                estacionViewModel.actualizarEstacion(item)
            } ?: estacionViewModel.insertarEstacion(Estacion(null, nombre.toString(), ciudad.toString()))
            ciudad.clear()
            nombre.clear()
        }
        binding.cancelEstacionButton.setOnClickListener{
            requireActivity().finish()
        }
    }

}