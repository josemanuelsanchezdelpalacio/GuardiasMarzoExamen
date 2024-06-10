package com.dam2jms.guardiasmarzoexamen.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dam2jms.guardiasmarzoexamen.data.Guardia
import com.dam2jms.guardiasmarzoexamen.data.Profesor
import com.dam2jms.guardiasmarzoexamen.data.listadoProfesores
import com.dam2jms.guardiasmarzoexamen.models.ViewModelFirstScreen
import com.dam2jms.guardiasmarzoexamen.models.ViewModelGuardia
import com.dam2jms.guardiasmarzoexamen.models.ViewModelSecondScreen
import com.dam2jms.guardiasmarzoexamen.navigation.AppScreens
import com.dam2jms.guardiasmarzoexamen.states.UiState
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(navController: NavHostController, mvvm: ViewModelSecondScreen) {

    var filtroTexto by rememberSaveable { mutableStateOf("") }
    var filtroProfesores by rememberSaveable { mutableStateOf(listadoProfesores) }

    //para que se pueda cerrar y abrir el menu desplegable
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    //para crear el menu desplegable
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContent(navController = navController)
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Guardias") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            content = { paddingValues ->
                //creo un edittext y un boton para filtrar el nombre del profesor
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    OutlinedTextField(
                        value = filtroTexto,
                        onValueChange = { filtroTexto = it },
                        label = { Text("filtrar nombre profesor") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Button(
                        onClick = { filtroProfesores = mvvm.filtroProfesores(filtroTexto) },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        Text("Filtrar")
                    }

                    secondScreenBodyContent(
                        modifier = Modifier.padding(paddingValues),
                        profesores = filtroProfesores
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate(AppScreens.NuevaGuardiaScreen.route) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Nueva guardia")
                }
            }
        )
    }
}

//para las opciones del menu desplegable
@Composable
fun DrawerContent(navController: NavHostController) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            NavigationDrawerItem(
                label = { Text(text = "Guardias") },
                selected = false,
                onClick = { navController.navigate(route = AppScreens.GuardiasScreen.route) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color(0xFF0C49FF))
        }
    }
}

//muestro un lazycolumn y muestro el nombre de cada profesor con la imagen respectiva de su horario
@Composable
fun secondScreenBodyContent(modifier: Modifier, profesores: List<Profesor>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(profesores) { profesor ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = profesor.nombreProfesor,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = profesor.horario),
                        contentDescription = "horario profesor",
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
        }
    }
}

