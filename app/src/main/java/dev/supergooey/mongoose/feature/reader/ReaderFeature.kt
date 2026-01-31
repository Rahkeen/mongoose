package dev.supergooey.mongoose.feature.reader

import dev.supergooey.mongoose.models.Page
import dev.supergooey.mongoose.settings.ReadingDirection

data class ReaderState(
    val mangaTitle: String = "",
    val chapterTitle: String = "",
    val pages: List<Page> = emptyList(),
    val currentPage: Int = 0,
    val showOverlay: Boolean = false,
    val readingDirection: ReadingDirection = ReadingDirection.Horizontal
)

sealed interface ReaderAction {
    data class PageChanged(val page: Int) : ReaderAction
    data object ToggleOverlay : ReaderAction
    data class SetReadingDirection(val direction: ReadingDirection) : ReaderAction
}
