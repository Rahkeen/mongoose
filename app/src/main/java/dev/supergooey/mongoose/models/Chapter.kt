package dev.supergooey.mongoose.models

data class Chapter(
    val id: String,
    val mangaId: String,
    val number: Int,
    val title: String,
    val pages: List<Page>
)
