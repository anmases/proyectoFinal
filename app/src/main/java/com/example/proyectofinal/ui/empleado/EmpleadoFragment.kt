package com.example.proyectofinal.ui.empleado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.FragmentEmpleadoBinding
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.services.empleado.EmpleadoViewModel


class EmpleadoFragment : Fragment() {
    private lateinit var empleadoViewModel: EmpleadoViewModel
    private var _binding: FragmentEmpleadoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEmpleadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        empleadoViewModel = ViewModelProvider(this).get(EmpleadoViewModel::class.java)
        binding.empleadoRecycler.layoutManager = LinearLayoutManager(requireActivity())
        empleadoViewModel.adapter.observe(viewLifecycleOwner) {
            binding.empleadoRecycler.adapter = it
        }
        empleadoViewModel.response.observe(viewLifecycleOwner){ response: ApiResponse ->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Eliminado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        empleadoViewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}