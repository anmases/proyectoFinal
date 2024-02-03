package com.example.proyectofinal.ui.tren

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.databinding.FragmentTrenFormBinding
import com.example.proyectofinal.models.Tren
import com.example.proyectofinal.repository.ApiResponse
import com.example.proyectofinal.services.tren.TrenViewModel


class TrenFormFragment : Fragment() {
    private var _binding: FragmentTrenFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //infla la vista del fragment.
        _binding = FragmentTrenFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trenViewModel = ViewModelProvider(this).get(TrenViewModel::class.java)
        var id: Int? = null
        val modelo = binding.textTrenModelo.text
        val capacidad = binding.textTrenCapacidad.text

//Recuperamos los datos del intent
        if(
            requireActivity().intent.hasExtra("ID") &&
            requireActivity().intent.hasExtra("MODEL") &&
            requireActivity().intent.hasExtra("CAPACITY")
        )
        {
            id = requireActivity().intent.getIntExtra("ID", -1)
            modelo.append(requireActivity().intent.getStringExtra("MODEL"), "")
            capacidad.append(requireActivity().intent.getStringExtra("CAPACITY"), "")
            //Le decimos a la instancia del viewmodel que este es el tren no modificado
            trenViewModel.setSelectedTren(
                Tren(
                    requireActivity().intent.getIntExtra("ID", 0),
                    requireActivity().intent.getStringExtra("MODEL")!!,
                    requireActivity().intent.getStringExtra("CAPACITY")!!.toInt()
            ))
        }



//Maneja las respuestas del botón guardar:
        trenViewModel.response.observe(viewLifecycleOwner){ response: ApiResponse->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Insertado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }
//Maneja la lógica del botón guardar:
        binding.submitTrenButton.setOnClickListener {
          id?.let {
              val item = Tren(it, modelo.toString(), capacidad.toString().toInt())
              trenViewModel.actualizarTren(item)
            } ?: trenViewModel.insertarTren(Tren(null, modelo.toString(), capacidad.toString().toInt()))
            modelo.clear()
            capacidad.clear()
        }
//Maneja la acción del botón eliminar:
        binding.cancelTrenButton.setOnClickListener{
            requireActivity().finish()
        }
    }



}