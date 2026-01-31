package dev.supergooey.mongoose.feature.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.supergooey.mongoose.data.SampleMangaData
import dev.supergooey.mongoose.settings.ReaderSettings
import dev.supergooey.mongoose.settings.ReadingDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReaderViewModel(
    private val mangaId: String,
    private val chapterId: String,
    private val startPage: Int,
    private val readerSettings: ReaderSettings
) : ViewModel() {
    private val _state = MutableStateFlow(ReaderState())
    val state: StateFlow<ReaderState> = _state.asStateFlow()

    init {
        loadChapter()
        observeReadingDirection()
    }

    private fun loadChapter() {
        val manga = SampleMangaData.getMangaById(mangaId)
        val chapter = SampleMangaData.getChapterById(mangaId, chapterId)

        if (manga != null && chapter != null) {
            _state.update { currentState ->
                currentState.copy(
                    mangaTitle = manga.title,
                    chapterTitle = chapter.title,
                    pages = chapter.pages,
                    currentPage = startPage
                )
            }
        }
    }

    private fun observeReadingDirection() {
        viewModelScope.launch {
            readerSettings.readingDirection.collect { direction ->
                _state.update { it.copy(readingDirection = direction) }
            }
        }
    }

    fun onAction(action: ReaderAction) {
        when (action) {
            is ReaderAction.PageChanged -> {
                _state.update { it.copy(currentPage = action.page) }
            }
            is ReaderAction.ToggleOverlay -> {
                _state.update { it.copy(showOverlay = !it.showOverlay) }
            }
            is ReaderAction.SetReadingDirection -> {
                viewModelScope.launch {
                    readerSettings.setReadingDirection(action.direction)
                }
            }
        }
    }

    class Factory(
        private val mangaId: String,
        private val chapterId: String,
        private val startPage: Int,
        private val readerSettings: ReaderSettings
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReaderViewModel(mangaId, chapterId, startPage, readerSettings) as T
        }
    }
}
