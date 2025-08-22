package com.gamecodeschool.arequipafind.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gamecodeschool.arequipafind.data.remote.firebase.FirebaseAuthDataSource
import com.gamecodeschool.arequipafind.data.repository.AuthRepositoryImpl
import com.gamecodeschool.arequipafind.domain.usecases.LoginUseCase
import com.gamecodeschool.arequipafind.domain.usecases.RegisterUseCase
import com.gamecodeschool.arequipafind.feature.home.ui.HomeScreen
import com.gamecodeschool.arequipafind.feature.login.LoginViewModel
import com.gamecodeschool.arequipafind.feature.login.ui.LoginScreen
import com.gamecodeschool.arequipafind.feature.profile.ProfileViewModel
import com.gamecodeschool.arequipafind.feature.profile.ui.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

//Navegacion de la APP
@Composable
fun AppNavGraph(navController: NavHostController){
    val firebaseAuth = FirebaseAuth.getInstance()
    val dataSource = FirebaseAuthDataSource(firebaseAuth)
    //val repository = AuthRepositoryImpl(dataSource)
    //val loginUseCase = LoginUseCase(repository)
    //val registerUseCase = RegisterUseCase(repository)
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ) {
        // LOGIN
        composable(NavRoutes.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginViewModel = viewModel,
                onLoginSuccess = {
                    viewModel.routeAfterAuth(
                        onGoHome = {
                            navController.navigate(NavRoutes.Home.route) {
                                popUpTo(NavRoutes.Login.route) { inclusive = true }
                            }
                        },
                        onGoProfile = {
                            navController.navigate(NavRoutes.Profile.route) {
                                popUpTo(NavRoutes.Login.route) { inclusive = true }
                            }
                        },
                        onError = { /* opcional: snackbar/toast */ }
                    )
                }
            )
        }

        // HOME
        composable(NavRoutes.Home.route) {
            HomeScreen()
        }

        // PROFILE
        composable(NavRoutes.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onProfileCompleted = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Profile.route) { inclusive = true }
                    }
                }
            )
        }
    }
}