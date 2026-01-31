package dev.supergooey.mongoose.models

/**
 * Represents a detected panel on a manga page.
 * Used for future panel-by-panel reading mode.
 */
data class Panel(
    val id: String,
    val pageId: String,
    val boundingBox: BoundingBox,
    val order: Int
)
