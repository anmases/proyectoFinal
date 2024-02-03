package com.example.proyectofinal.ui.estacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.FragmentEstacionBinding
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.services.estacion.EstacionViewModel
import com.example.proyectofinal.ui.FormActivity


class EstacionFragment : Fragment() {

    private var _binding: FragmentEstacionBinding? = null
    private val binding get() = _binding!!
    private lateinit var estacionViewModel: EstacionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEstacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estacionViewModel = ViewModelProvider(this).get(EstacionViewModel::class.java)
        binding.estacionRecycler.layoutManager = LinearLayoutManager(requireActivity())
        estacionViewModel.adapter.observe(viewLifecycleOwner) {
            binding.estacionRecycler.adapter = it
        }
        estacionViewModel.response.observe(viewLifecycleOwner){ response: ApiResponse ->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Eliminado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }
        //Maneja la respuesta al click sobre un elemento:
        estacionViewModel.selectedEstacion.observe(viewLifecycleOwner){estacion ->
            val intent = Intent(requireActivity(), FormActivity::class.java).apply {
                putExtra("FORM_TYPE", "ESTACION")
                putExtra("ID", estacion.id)
                putExtra("NAME", estacion.nombre)
                putExtra("CITY", estacion.ciudad)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        estacionViewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}