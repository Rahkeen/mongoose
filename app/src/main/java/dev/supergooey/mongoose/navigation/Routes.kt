package dev.supergooey.mongoose.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ShelfRoute

@Serializable
data class ReaderRoute(
    val mangaId: String,
    val chapterId: String,
    val startPage: Int = 0
)
