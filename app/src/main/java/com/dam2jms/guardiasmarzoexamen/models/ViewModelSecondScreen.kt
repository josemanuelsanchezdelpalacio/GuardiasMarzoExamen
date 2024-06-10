package com.dam2jms.guardiasmarzoexamen.models

import androidx.lifecycle.ViewModel
import com.dam2jms.guardiasmarzoexamen.data.Profesor
import com.dam2jms.guardiasmarzoexamen.data.listadoProfesores
import com.dam2jms.guardiasmarzoexamen.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelSecondScreen : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    //metodo para filtrar el profesor a traves de su nombre
    fun filtroProfesores(filtroTexto: String): List<Profesor> {
        return listadoProfesores.filter { profesor ->
            profesor.nombreProfesor.contains(filtroTexto, ignoreCase = true)
        }
    }
}

