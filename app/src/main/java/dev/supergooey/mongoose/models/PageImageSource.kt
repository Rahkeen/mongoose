package dev.supergooey.mongoose.models

sealed interface PageImageSource {
    data class LocalDrawable(val resourceId: Int) : PageImageSource
    data class RemoteUrl(val url: String) : PageImageSource
}
