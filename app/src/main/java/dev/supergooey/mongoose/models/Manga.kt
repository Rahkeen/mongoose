package dev.supergooey.mongoose.models

data class Manga(
    val id: String,
    val title: String,
    val coverResourceId: Int,
    val chapters: List<Chapter>
)
