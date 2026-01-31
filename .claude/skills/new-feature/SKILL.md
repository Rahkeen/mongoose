# New Feature Skill

Scaffolds a new feature module following the Mongoose app architecture patterns.

## Usage

```
/new-feature <FeatureName>
```

## What it creates

For a feature named `Foo`:

### 1. `feature/foo/FooFeature.kt`
```kotlin
package dev.supergooey.mongoose.feature.foo

import androidx.compose.runtime.Immutable

@Immutable
data class FooState(
    // Add state properties
)

sealed interface FooAction {
    // Add actions
}
```

### 2. `feature/foo/FooViewModel.kt`
```kotlin
package dev.supergooey.mongoose.feature.foo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.supergooey.mongoose.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FooViewModel(
    // Add dependencies
) : ViewModel() {

    private val _state = MutableStateFlow(FooState())
    val state: StateFlow<FooState> = _state.asStateFlow()

    fun send(action: FooAction) {
        when (action) {
            // Handle actions
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as App
                FooViewModel(
                    // Inject dependencies from app
                )
            }
        }
    }
}
```

### 3. `feature/foo/FooScreen.kt`
```kotlin
package dev.supergooey.mongoose.feature.foo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FooRoute(
    viewModel: FooViewModel = viewModel(factory = FooViewModel.Factory)
) {
    val state by viewModel.state.collectAsState()
    FooScreen(
        state = state,
        onAction = viewModel::send
    )
}

@Composable
fun FooScreen(
    state: FooState,
    onAction: (FooAction) -> Unit
) {
    // Implement UI
}
```

## After scaffolding

1. Add a route to `navigation/Routes.kt`:
   ```kotlin
   @Serializable
   data object FooRoute
   ```

2. Add entry to `navigation/AppNavigation.kt`:
   ```kotlin
   entry<FooRoute> {
       FooRoute()
   }
   ```

## Architecture patterns

- **State**: Immutable data class annotated with `@Immutable`
- **Actions**: Sealed interface for all user/system events
- **ViewModel**: Factory companion object for dependency injection from App
- **Screen**: Split into Route (wiring) and Screen (pure UI) composables
