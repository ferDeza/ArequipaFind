package com.gamecodeschool.arequipafind.feature.jobs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import com.gamecodeschool.arequipafind.feature.jobs.CreateJobViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun CreateJobScreen(
    navController: NavHostController,
    viewModel: CreateJobViewModel = hiltViewModel()
){
   val isLoading by viewModel.isLoading.collectAsState(initial = false)
   val context = LocalContext.current
   val scope = rememberCoroutineScope()
   val snackbarHostState = remember { SnackbarHostState() }
   var title by remember { mutableStateOf("") }
   var description by remember { mutableStateOf("") }
   var priceText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { ev ->
            when (ev) {
                is CreateJobViewModel.CreateJobUiEvent.Saved -> {
                    navController.popBackStack()
                }
                is CreateJobViewModel.CreateJobUiEvent.Error -> {
                    // Simple fallback: toast/snackbar (you can implement SnackbarHost)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = ev.message ?: "Error al guardar el trabajo"
                        )
                    }
                }
            }
        }
    }
    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
    { padding ->
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 6
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = { Text("Precio (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            val price = priceText.toDoubleOrNull()
            if (title.isBlank()) {
                scope.launch { viewModel._uiEvent.emit(CreateJobViewModel.CreateJobUiEvent.Error("Título requerido")) } // if _uiEvent is private, change to a public convenience
                return@Button
            }
            viewModel.createJob(title.trim(), description.trim(), price)
        }, modifier = Modifier.fillMaxWidth(), enabled = !isLoading) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text("Publicar trabajo")
        }
    }
    }
}