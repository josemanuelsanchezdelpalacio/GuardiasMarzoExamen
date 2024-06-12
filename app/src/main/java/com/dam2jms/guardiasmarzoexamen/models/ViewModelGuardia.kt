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

    //funcion para actualizar las guardias
    fun onDatosGuardia(profesor: String, fecha: String, time: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nombreProfesor = profesor,
                fecha = fecha,
                time = time
            )
        }
    }

    //funcion para añadir una guardia
    fun onAddGuardia(context: Context, fecha: String, time: String) {
        val profesor = listadoProfesores.find { it.nombreProfesor == _uiState.value.nombreProfesor }

        if (profesor != null) {
            if (_uiState.value.nombreProfesor.isNotEmpty()) {
                //compruebo que el profesor exista
                val existingGuardia = allGuardias.find { it.nombreProfesor == _uiState.value.nombreProfesor }

                if (existingGuardia != null) {
                    //actualizacion de la guardia
                    existingGuardia.date = fecha
                    existingGuardia.hour = time
                    existingGuardia.numeroGuardias++
                } else {
                    //creacion de una nueva guardia
                    val nuevaGuardia = Guardia(
                        nombreProfesor = _uiState.value.nombreProfesor,
                        date = fecha,
                        hour = time,
                        numeroGuardias = 1
                    )
                    allGuardias.add(nuevaGuardia)
                }

                Toast.makeText(context, "Guardia actualizada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Alguno de los campos está vacío", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "El profesor no existe en la lista", Toast.LENGTH_SHORT).show()
        }
    }
}
