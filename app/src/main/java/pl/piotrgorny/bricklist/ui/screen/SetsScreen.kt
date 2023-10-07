package pl.piotrgorny.bricklist.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.bricklist.SetsViewModel
import pl.piotrgorny.bricklist.ui.view.SetsList

@Composable
fun SetsScreen(navigateToSet: (setId: String) -> Unit) {
    val viewModel: SetsViewModel = viewModel(factory = SetsViewModel.Factory(LocalContext.current))
    val sets = viewModel.viewState.collectAsState(initial = emptyList()).value
    SetsList(sets) {
        navigateToSet(it.id)
    }

    FloatingActionButton(onClick = { viewModel.addSet() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}