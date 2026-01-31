package dev.supergooey.mongoose.models

/**
 * Represents a bounding box with normalized coordinates (0-1).
 * Used for panel detection overlay.
 */
data class BoundingBox(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)
