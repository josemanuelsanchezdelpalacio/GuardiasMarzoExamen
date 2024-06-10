package com.dam2jms.guardiasmarzoexamen.models

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.dam2jms.guardiasmarzoexamen.data.Guardia
import com.dam2jms.guardiasmarzoexamen.data.Profesor
import com.dam2jms.guardiasmarzoexamen.data.allGuardias
import com.dam2jms.guardiasmarzoexamen.data.listadoProfesores
import com.dam2jms.guardiasmarzoexamen.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

class ViewModelGuardia : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    //para actualizar las guardias añadidas
    fun onDatosGuardia(profesor: String, fecha: String, time: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nombreProfesor = profesor,
                fecha = fecha,
                time = time
            )
        }
    }

    //busca si el profesor existe y si existe añade los datos del nombre, la fecha, la hora y el numero de guardias a la lista
    fun onAddGuardia(context: Context, fecha: String, time: String) {

        val profesor = listadoProfesores.find { it.nombreProfesor == _uiState.value.nombreProfesor }

        if (profesor != null) {
            if (_uiState.value.nombreProfesor.isNotEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(numeroGuardias = _uiState.value.numeroGuardias.inc())
                }
                val nuevaGuardia = Guardia(
                    nombreProfesor = _uiState.value.nombreProfesor,
                    date = fecha,
                    hour = time,
                    numeroGuardias = _uiState.value.numeroGuardias
                )
                allGuardias.add(nuevaGuardia)
                Toast.makeText(context, "Nueva guardia añadida", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Alguno de los campos está vacio", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "El profesor no existe en la lista", Toast.LENGTH_SHORT).show()
        }
    }
}
