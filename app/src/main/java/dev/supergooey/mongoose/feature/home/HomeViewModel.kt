package dev.supergooey.mongoose.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.supergooey.mongoose.App
import dev.supergooey.mongoose.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        observeItems()
    }

    private fun observeItems() {
        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                _state.update { it.copy(items = items, isLoading = false) }
            }
        }
    }

    fun send(action: HomeAction) {
        when (action) {
            is HomeAction.ItemClicked -> {
                // Navigation handled by UI layer
            }
            is HomeAction.Refresh -> {
                _state.update { it.copy(isLoading = true) }
                // Trigger refresh logic here
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as App
                HomeViewModel(app.itemRepository)
            }
        }
    }
}
