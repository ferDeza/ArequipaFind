package com.gamecodeschool.arequipafind.feature.review.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.gamecodeschool.arequipafind.feature.review.ReviewViewModel

@Composable
fun ReviewScreen(
    navController: NavHostController,
    jobId: String,
    reviewedId: String,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    var rating by remember { mutableStateOf(5) }
    var comment by remember { mutableStateOf("") }
    val ctx = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { ev ->
            when (ev) {
                is ReviewViewModel.ReviewUiEvent.Saved -> {
                    android.widget.Toast.makeText(ctx, "Review guardada", android.widget.Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
                is ReviewViewModel.ReviewUiEvent.Error -> android.widget.Toast.makeText(ctx, ev.message, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Califica al usuario", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Row {
            (1..5).forEach { i ->
                IconButton(onClick = { rating = i }) {
                    Icon(
                        imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = comment, onValueChange = { comment = it }, label = { Text("Comentario (opcional)") }, modifier = Modifier.fillMaxWidth(), maxLines = 4)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { viewModel.submitReview(jobId, reviewedId, rating, comment) }, modifier = Modifier.fillMaxWidth()) {
            Text("Enviar review")
        }
    }
}