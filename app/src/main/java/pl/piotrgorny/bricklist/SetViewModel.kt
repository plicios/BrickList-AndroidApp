package pl.piotrgorny.bricklist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pl.piotrgorny.bricklist.data.BrickSet
import pl.piotrgorny.bricklist.data.Part
import pl.piotrgorny.bricklist.data.SetRepository
import pl.piotrgorny.bricklist.data.SetRepositoryRetrofitWithDatabase

class SetViewModel(private val setId: String, private val setRepository: SetRepository) : ViewModel() {
    private val _viewState: Flow<BrickSet?> = setRepository.getSetWithParts(setId)
    val viewState: Flow<BrickSet?> = _viewState

    fun updatePart(part: Part) {
        viewModelScope.launch(Dispatchers.IO) {
            setRepository.updatePart(part, setId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val setId: String, private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SetViewModel(setId, SetRepositoryRetrofitWithDatabase(context.applicationContext)) as T
        }
    }
}