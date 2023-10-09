package pl.piotrgorny.bricklist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.Flow
import pl.piotrgorny.bricklist.data.Part
import pl.piotrgorny.bricklist.data.SetRepository
import pl.piotrgorny.bricklist.data.SetRepositoryRetrofitWithDatabase

class MissingPartsViewModel(private val setRepository: SetRepository) : ViewModel() {
    private val _viewState: Flow<List<Part>> = setRepository.getAllMissingParts()
    val viewState: Flow<List<Part>> = _viewState

    @Suppress("UNCHECKED_CAST")
    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MissingPartsViewModel(SetRepositoryRetrofitWithDatabase(context.applicationContext)) as T
        }
    }
}