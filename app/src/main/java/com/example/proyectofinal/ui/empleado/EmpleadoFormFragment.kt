package com.example.proyectofinal.ui.empleado

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.databinding.FragmentEmpleadoFormBinding
import com.example.proyectofinal.models.Empleado
import com.example.proyectofinal.models.Estacion
import com.example.proyectofinal.services.empleado.EmpleadoViewModel
import com.example.proyectofinal.services.estacion.EstacionViewModel
import java.util.Calendar


class EmpleadoFormFragment : Fragment() {
    private var _binding: FragmentEmpleadoFormBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //infla la vista del fragment.
        _binding = FragmentEmpleadoFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel para los empleados:
        val empleadoViewModel = ViewModelProvider(this).get(EmpleadoViewModel::class.java)
        //ViewModel para las estaciones del select:
        val estacionViewModel = ViewModelProvider(this).get(EstacionViewModel::class.java)

//Campos
        var id: Int? = null
        val contratado = binding.textEmpleadoContratado.text
        val nombre = binding.textEmpleadoNombre.text
        val puesto = binding.textEmpleadoPuesto.text
        var estacion_id: Int? = null

        //Recuperamos los datos del intent
        if(
            requireActivity().intent.hasExtra("ID") &&
            requireActivity().intent.hasExtra("HIRED") &&
            requireActivity().intent.hasExtra("NAME") &&
            requireActivity().intent.hasExtra("TITLE") &&
            requireActivity().intent.hasExtra("STATION_ID")
        )
        {
            id = requireActivity().intent.getIntExtra("ID", 0)
            contratado.append(requireActivity().intent.getStringExtra("HIRED"), "")
            nombre.append(requireActivity().intent.getStringExtra("NAME"), "")
            puesto.append(requireActivity().intent.getStringExtra("TITLE"))
            estacion_id = requireActivity().intent.getIntExtra("STATION_ID", 0)
            empleadoViewModel.setSelectedEmpleado(
                Empleado(
                    requireActivity().intent.getIntExtra("ID", 0),
                    requireActivity().intent.getStringExtra("HIRED")!!,
                    requireActivity().intent.getStringExtra("NAME")!!,
                    requireActivity().intent.getStringExtra("TITLE")!!,
                    requireActivity().intent.getIntExtra("STATION_ID", 0)
                )
            )
        }


        empleadoViewModel.response.observe(viewLifecycleOwner){ response ->
            if(response.result == "ok"){
                Toast.makeText(requireActivity(), "Insertado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(), "Error de inserción", Toast.LENGTH_SHORT).show()
            }
        }

        val select = binding.multiSelectEstacion
        estacionViewModel.estaciones.observe(viewLifecycleOwner) { estaciones ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estaciones.map { it.nombre })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            select.adapter = adapter
            //Comprobamos si está el id de la estación en la lista y seleccionamos su posición
            if(estacion_id != null) {
                for ((index, estacion) in estaciones.withIndex()) {
                    if(estacion.id == estacion_id) {
                        select.setSelection(index)
                        break
                    }
                }
            }
        }
        val contratadoCampo = binding.textEmpleadoContratado
        contratadoCampo.setOnClickListener{
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)
            // Crear y mostrar el DatePickerDialog
            val datePickerDialog = DatePickerDialog(requireActivity(), { _, year, monthOfYear, dayOfMonth ->
                // Formatear la fecha seleccionada y mostrarla en el EditText
                val fechaSeleccionada = "${dayOfMonth}/${monthOfYear + 1}/${year}"
                contratado.clear()
                contratado.append(fechaSeleccionada)
            }, year, month, day)
            datePickerDialog.show()
        }

        binding.submitEmpleadoButton.setOnClickListener {
            val posicion =  select.selectedItemPosition

            //Volvemos a llamar al viewmodel para conocer el id al que corresponde el item seleccionado:
            estacionViewModel.estaciones.observe(viewLifecycleOwner){ estaciones ->
                val idEstacion = estaciones[posicion].id
                if(idEstacion != null){

                    id?.let {
                        val item = Empleado(it, contratado.toString(), nombre.toString(), puesto.toString(), idEstacion)
                        empleadoViewModel.actualizarEmpleado(item)
                    } ?:empleadoViewModel.insertarEmpleado(Empleado(null, contratado.toString(), nombre.toString(), puesto.toString(), idEstacion))

                    contratado.clear()
                    nombre.clear()
                    puesto.clear()
                    select.setSelection(0)
                }
            }

        }

        binding.cancelEmpleadoButton.setOnClickListener{
            requireActivity().finish()
        }
    }
}