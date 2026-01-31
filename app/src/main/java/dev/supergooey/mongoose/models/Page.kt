package dev.supergooey.mongoose.models

data class Page(
    val id: String,
    val chapterId: String,
    val index: Int,
    val imageSource: PageImageSource
)
