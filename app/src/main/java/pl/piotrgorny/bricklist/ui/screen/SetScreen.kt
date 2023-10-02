package pl.piotrgorny.bricklist.ui.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.bricklist.SetViewModel
import pl.piotrgorny.bricklist.ui.view.PartsList

@Composable
fun SetScreen(){
    val viewModel: SetViewModel = viewModel()
    PartsList(items = viewModel.viewState.value)
}