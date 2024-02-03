package com.example.proyectofinal.ui.tren

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.databinding.FragmentTrenBinding
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.services.tren.TrenViewModel
import com.example.proyectofinal.ui.FormActivity

//Contiene to-do lo relacionado con la interfaz.
class TrenFragment : Fragment() {

    private var _binding: FragmentTrenBinding? = null
    private val binding get() = _binding!!
    private lateinit var trenViewModel: TrenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//infla la vista del fragment.
        _binding = FragmentTrenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trenViewModel = ViewModelProvider(this).get(TrenViewModel::class.java)
        binding.trenRecycler.layoutManager = LinearLayoutManager(requireActivity())
//observa el adapter.
        trenViewModel.adapter.observe(viewLifecycleOwner) {
             binding.trenRecycler.adapter= it
        }
//Maneja las respuestas del botón eliminar:
        trenViewModel.response.observe(viewLifecycleOwner){ response: ApiResponse ->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Eliminado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }
//Maneja la respuesta al click sobre un elemento:
        trenViewModel.selectedTren.observe(viewLifecycleOwner){tren ->
            val intent = Intent(requireActivity(), FormActivity::class.java).apply {
                putExtra("FORM_TYPE", "TREN")
                putExtra("ID", tren.id)
                putExtra("MODEL", tren.modelo)
                putExtra("CAPACITY", tren.capacidad.toString())
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
//Refresca los cambios tras volver de otra activity:
        trenViewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}