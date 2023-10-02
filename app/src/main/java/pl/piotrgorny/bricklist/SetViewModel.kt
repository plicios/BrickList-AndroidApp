package pl.piotrgorny.bricklist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.piotrgorny.bricklist.data.Part
import pl.piotrgorny.bricklist.data.SetRepositoryRetrofit

class SetViewModel : ViewModel() {
    private val _viewState: MutableState<List<Part>> = mutableStateOf(emptyList())
    val viewState: State<List<Part>> = _viewState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = SetRepositoryRetrofit.getSetParts("7140")
        }
    }
}