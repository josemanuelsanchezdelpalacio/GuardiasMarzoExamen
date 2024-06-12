package com.dam2jms.guardiasmarzoexamen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dam2jms.guardiasmarzoexamen.data.Profesor
import com.dam2jms.guardiasmarzoexamen.data.allGuardias
import com.dam2jms.guardiasmarzoexamen.data.listadoProfesores
import com.dam2jms.guardiasmarzoexamen.models.ViewModelFirstScreen
import com.dam2jms.guardiasmarzoexamen.models.ViewModelGuardia
import com.dam2jms.guardiasmarzoexamen.states.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardiasScreen(navController: NavHostController, mvvm: ViewModelGuardia) {

    val uiState by mvvm.uiState.collectAsState()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "GUARDAS REGISTRADAS") },
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
            guardiasScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, uiState)
        }
    }
}

//con un lazycolumn muestra una tarjeta con la guardia añadida en la ventana NuevaGuardia
@Composable
fun guardiasScreenBodyContent(modifier: Modifier, mvvm: ViewModelGuardia, uiState: UiState) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        items(allGuardias){ guardia ->
            ElevatedCard (
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = guardia.nombreProfesor,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Fecha: ${guardia.date}")
                    Text(text = "Hora: ${guardia.hour}")
                    Text(text = "Numero de guardias realizadas: ${guardia.numeroGuardias}")
                }
            }
        }
    }
}
