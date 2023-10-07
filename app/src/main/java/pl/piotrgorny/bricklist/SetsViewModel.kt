package pl.piotrgorny.bricklist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.piotrgorny.bricklist.data.BrickSet
import pl.piotrgorny.bricklist.data.SetRepository
import pl.piotrgorny.bricklist.data.SetRepositoryRetrofitWithDatabase

class SetsViewModel(private val setRepository: SetRepository) : ViewModel() {
    private val _viewState: Flow<List<BrickSet>> = setRepository.getSets()
    val viewState: Flow<List<BrickSet>> = _viewState

    fun addSet() {
        viewModelScope.launch(Dispatchers.IO) {
            setRepository.addSet("7140-1").collect {

            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SetsViewModel(SetRepositoryRetrofitWithDatabase(context.applicationContext)) as T
        }
    }
}