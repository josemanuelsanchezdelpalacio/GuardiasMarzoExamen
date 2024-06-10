package com.dam2jms.guardiasmarzoexamen.data

import android.app.DatePickerDialog
import android.app.TimePickerDialog

//objeto con el nombre del profesor, la fecha, la hora y el numero de guardias
data class Guardia(
    var nombreProfesor: String,
    var date: String,
    var hour: String,
    var numeroGuardias: Int
)
