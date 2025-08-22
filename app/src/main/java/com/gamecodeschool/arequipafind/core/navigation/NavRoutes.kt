package com.gamecodeschool.arequipafind.core.navigation


//Rutas de navegacion de la aplicacion

sealed class NavRoutes(val route: String) {
    object Login: NavRoutes("Login")
    object Home: NavRoutes ("Home")
    object Register: NavRoutes("Register")
    object Profile:NavRoutes("Profile")



}