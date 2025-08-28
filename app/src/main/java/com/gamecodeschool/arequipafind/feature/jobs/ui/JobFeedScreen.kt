package com.gamecodeschool.arequipafind.feature.jobs.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gamecodeschool.arequipafind.core.navigation.NavRoutes
import com.gamecodeschool.arequipafind.feature.jobs.JobFeedViewModel

@Composable
fun JobFeedScreen(
    navController: NavHostController,
    viewModel: JobFeedViewModel = hiltViewModel()
){
    val jobs by viewModel.jobs.collectAsState(initial= emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val error by viewModel.error.collectAsState(initial = null)
    LaunchedEffect(Unit) { viewModel.loadJobs() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NavRoutes.CreateJob) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Crear Job")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (!isLoading && jobs.isEmpty()) {
                Text("No hay trabajos disponibles", modifier = Modifier.align(Alignment.Center))
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(jobs) { job ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate(NavRoutes.JobDetail.createRoute(job.id)) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(job.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text(job.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                Text("Estado: ${job.status}")
                                Text("Owner: ${job.ownerId}")
                            }
                        }
                    }
                }
            }

            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp))
            }
        }
    }
}