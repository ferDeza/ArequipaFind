package com.gamecodeschool.arequipafind.feature.jobs.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import com.gamecodeschool.arequipafind.core.navigation.NavRoutes
import com.gamecodeschool.arequipafind.feature.jobs.JobDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun JobDetailScreen (
    navController: NavHostController,
    jobId:String,
    viewModel: JobDetailViewModel = hiltViewModel()

){
    val job by viewModel.job.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState(initial= false)
    val context= LocalContext.current

    LaunchedEffect (jobId){ viewModel.load(jobId) }
    LaunchedEffect (Unit){
        viewModel.uiEvent.collect{ ev->
            when(ev){
                is JobDetailViewModel.JobUiEvent.Assigned->{
                    android.widget.Toast.makeText(context,"Trabajo aceptado ", android.widget.Toast.LENGTH_SHORT).show()
                }
                is JobDetailViewModel.JobUiEvent.Completed ->{
                    android.widget.Toast.makeText(context,"Trabajo completado", android.widget.Toast.LENGTH_SHORT).show()
                    job?.let { j->
                        val myId = viewModel.getCurrentUserId()
                        if (j.ownerId == myId && !j.workerId.isNullOrBlank()) {
                            navController.navigate(NavRoutes.Review.createRoute(j.id, j.workerId!!))
                        }
                    }
                }
                is JobDetailViewModel.JobUiEvent.Error -> {
                    android.widget.Toast.makeText(context, ev.message, android.widget.Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    job?.let { j ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(j.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text(j.description)
            Spacer(Modifier.height(12.dp))
            Text("Estado: ${j.status}")
            Text("Owner: ${j.ownerId}")
            Text("Worker: ${j.workerId ?: "Sin asignar"}")
            Spacer(Modifier.height(16.dp))

            val myId = viewModel.getCurrentUserId()
            if (j.status == "pending" && myId != j.ownerId) {
                Button(onClick = { viewModel.acceptJob() }, modifier = Modifier.fillMaxWidth()) { Text("Aceptar trabajo") }
            }
            if (j.status == "assigned" && j.workerId == myId) {
                Button(onClick = { viewModel.completeJob() }, modifier = Modifier.fillMaxWidth()) { Text("Marcar completado") }
            }
        }
    } ?: run {
        if (isLoading) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }


















}