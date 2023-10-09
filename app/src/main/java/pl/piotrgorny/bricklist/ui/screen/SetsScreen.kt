package pl.piotrgorny.bricklist.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.bricklist.SetsViewModel
import pl.piotrgorny.bricklist.ui.view.SetsList

@Composable
fun SetsScreen(navigateToSet: (setId: String) -> Unit, navigateToMissingParts: () -> Unit) {
    val viewModel: SetsViewModel = viewModel(factory = SetsViewModel.Factory(LocalContext.current))
    val sets = viewModel.viewState.collectAsState(initial = emptyList()).value
    Scaffold(floatingActionButton = {
        Column {
            FloatingActionButton(onClick = { navigateToMissingParts() }) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "Missing parts")
            }
            FloatingActionButton(onClick = { viewModel.addSet() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }) {
        Box(Modifier.padding(it)) {
            SetsList(sets) { brickSet ->
                navigateToSet(brickSet.id)
            }
        }
    }
}