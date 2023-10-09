package pl.piotrgorny.bricklist.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import pl.piotrgorny.bricklist.MissingPartsViewModel
import pl.piotrgorny.bricklist.SetViewModel
import pl.piotrgorny.bricklist.ui.view.PartsList
import pl.piotrgorny.bricklist.ui.view.SetInfo

@Composable
fun MissingPartsScreen(){
    val viewModel: MissingPartsViewModel = viewModel(factory = MissingPartsViewModel.Factory(LocalContext.current))
    val parts = viewModel.viewState.collectAsState(initial = emptyList()).value
    Column {
        PartsList(items = parts) {
        }
    }
}