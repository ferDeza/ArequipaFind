package com.gamecodeschool.arequipafind.core.navigation


//Rutas de navegacion de la aplicacion

sealed class NavRoutes(val route: String) {
    object Login: NavRoutes("Login")
    object Home: NavRoutes ("Home")
    object Register: NavRoutes("Register")
    object Profile:NavRoutes("Profile")
    object Jobs : NavRoutes("jobs")                       // feed
    object CreateJob : NavRoutes("create_job")
    object JobDetail : NavRoutes("job_detail/{jobId}") {
        fun createRoute(jobId: String) = "job_detail/$jobId"
    }
    object Review : NavRoutes("review/{jobId}/{reviewedId}") {
        fun createRoute(jobId: String, reviewedId: String) = "review/$jobId/$reviewedId"
    }
}