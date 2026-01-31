package dev.supergooey.mongoose.feature.home

import androidx.compose.runtime.Immutable
import dev.supergooey.mongoose.models.Item

@Immutable
data class HomeState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface HomeAction {
    data class ItemClicked(val id: String) : HomeAction
    data object Refresh : HomeAction
}
