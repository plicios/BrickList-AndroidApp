package pl.piotrgorny.bricklist.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect
import pl.piotrgorny.bricklist.SetViewModel
import pl.piotrgorny.bricklist.ui.view.PartsList
import pl.piotrgorny.bricklist.ui.view.SetInfo

@Composable
fun SetScreen(setId: String){
    val viewModel: SetViewModel = viewModel(factory = SetViewModel.Factory(setId, LocalContext.current))
    val brickSet = viewModel.viewState.collectAsState(initial = null).value
    val parts = brickSet?.parts ?: emptyList()
    Column {
        brickSet?.let {
            SetInfo(brickSet = it)
        }
        PartsList(items = parts) {
            viewModel.updatePart(it)
        }
    }
}