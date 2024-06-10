package com.dam2jms.guardiasmarzoexamen.navigation
sealed class AppScreens (val route: String){

    object FirstScreen: AppScreens(route = "first_screen")
    object SecondScreen: AppScreens(route = "second_screen")
    object NuevaGuardiaScreen: AppScreens(route = "nueva_guardia_screen")
    object GuardiasScreen: AppScreens(route = "guardia_screen")
}
