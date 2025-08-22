package com.gamecodeschool.arequipafind.feature.profile.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gamecodeschool.arequipafind.domain.models.UserProfile
import com.gamecodeschool.arequipafind.feature.profile.ProfileViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor() // necesario para que se alinee bien
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onProfileCompleted: () -> Unit
) {
    val profile by viewModel.profile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var name by remember { mutableStateOf(profile?.name ?: "") }
    var role by remember { mutableStateOf(profile?.role ?: "contratista") }

    var selectedSkills by remember{ mutableStateOf(profile?.skills?: emptyList()) }
    val roleOptions = listOf("contratista","trabajador")
    val skillsOptions= listOf("albañil","pintor","gasfitero","drywallero")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }

        if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        }

        Text(text = "Perfil de usuario", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "Rol",
            options = roleOptions,
            selectedOption = role,
            onOptionSelected = { role = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Habilidades")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skillsOptions.forEach { skill ->
                val isSelected = skill in selectedSkills
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedSkills = if (isSelected) {
                            selectedSkills - skill
                        } else {
                            selectedSkills + skill
                        }
                    },
                    label = { Text(skill) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val uid = viewModel.currentUid() ?: ""
                val updated = profile?.copy(
                    name = name,
                    role = role,
                    skills = selectedSkills,
                    updatedAt = System.currentTimeMillis()
                ) ?: UserProfile(
                    id = uid,
                    name = name,
                    email = profile?.email ?: "", // o obtener desde auth si quieres
                    photoUrl = profile?.photoUrl,
                    role = role,
                    skills = selectedSkills,
                    updatedAt = System.currentTimeMillis()
                )
                viewModel.updateProfile(updated)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) CircularProgressIndicator(modifier = Modifier.size(20.dp)) else Text("Guardar cambios")
        }

        error?.let { Text(text = it, color = MaterialTheme.colorScheme.error) }
    }
}