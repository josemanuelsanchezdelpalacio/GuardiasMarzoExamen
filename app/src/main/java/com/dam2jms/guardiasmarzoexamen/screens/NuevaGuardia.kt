package com.dam2jms.guardiasmarzoexamen.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dam2jms.guardiasmarzoexamen.models.ViewModelGuardia
import com.dam2jms.guardiasmarzoexamen.states.UiState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaGuardiaScreen(navController: NavHostController, mvvm: ViewModelGuardia) {

    val uiState by mvvm.uiState.collectAsState()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "CREAR GUARDIA") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atras")
                }
            },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            nuevaGuardiaScreenBodyContent(modifier = Modifier.padding(paddingValues), navController, mvvm, uiState)
        }
    }
}

//le pido al usuario que introduzca el nombre de un profesor, una fecha y una hora y guardo esos datos como una nueva guardia
@Composable
fun nuevaGuardiaScreenBodyContent(modifier: Modifier, navController: NavHostController, mvvm: ViewModelGuardia, uiState: UiState) {
    Column(modifier = modifier.padding(15.dp)) {

        var fecha by rememberSaveable { mutableStateOf("") }
        var time by rememberSaveable { mutableStateOf("") }
        val context = LocalContext.current

        val mCalendar = Calendar.getInstance()
        val anio = mCalendar.get(Calendar.YEAR)
        val mes = mCalendar.get(Calendar.MONTH)
        val dia = mCalendar.get(Calendar.DAY_OF_MONTH)

        var mDatePickerDialog =
            DatePickerDialog(LocalContext.current, { _, anio: Int, mes: Int, dia: Int ->
                fecha = "$dia/${mes + 1}/$anio"
            }, anio, mes, dia)
        var mHourPickerDialog =
            TimePickerDialog(LocalContext.current, { _, hora: Int, minuto: Int ->
                time = "$hora:$minuto"
            }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true)


        Text(
            text = "Creacion de nueva guardia",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = uiState.nombreProfesor,
            onValueChange = { mvvm.onDatosGuardia(it, fecha = fecha, time = time) },
            label = { Text(text = "Introduce tu nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(value = fecha, onValueChange = { mvvm.onDatosGuardia(profesor = uiState.nombreProfesor, it, time = time) },
                readOnly = true, label = {
                    Text(text = "Elige fecha")
                })
            Icon(imageVector = Icons.Filled.DateRange,
                contentDescription = "Elige fecha",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable {
                        mDatePickerDialog.show()
                    })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(value = time, onValueChange = { mvvm.onDatosGuardia(profesor = uiState.nombreProfesor, fecha = fecha, it) },
                readOnly = true, label = { Text(text = "Elige hora") })
            Icon(imageVector = Icons.Filled.DateRange,
                contentDescription = "Elige hora",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable {
                        mHourPickerDialog.show()
                    }
            )
        }

        Button(onClick = {
            mvvm.onAddGuardia(context = context, fecha, time)
        }) {
            Text(text = "Guardar evento")
        }
    }
}
