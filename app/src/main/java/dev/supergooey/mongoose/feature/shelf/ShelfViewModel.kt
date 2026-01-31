package dev.supergooey.mongoose.feature.shelf

import androidx.lifecycle.ViewModel
import dev.supergooey.mongoose.data.SampleMangaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShelfViewModel : ViewModel() {
    private val _state = MutableStateFlow(ShelfState())
    val state: StateFlow<ShelfState> = _state.asStateFlow()

    init {
        loadManga()
    }

    private fun loadManga() {
        _state.value = ShelfState(
            mangaList = SampleMangaData.allManga,
            isLoading = false
        )
    }
}
