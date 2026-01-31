package dev.supergooey.mongoose.feature.shelf

import dev.supergooey.mongoose.models.Manga

data class ShelfState(
    val mangaList: List<Manga> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface ShelfAction {
    data class MangaClicked(val mangaId: String, val chapterId: String) : ShelfAction
}
