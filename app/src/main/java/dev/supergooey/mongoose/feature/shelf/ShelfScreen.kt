package dev.supergooey.mongoose.feature.shelf

import android.app.Activity
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.supergooey.mongoose.feature.shelf.components.MangaCoverCard
import dev.supergooey.mongoose.feature.shelf.components.ShelfTopBar

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShelfRoute(
    onMangaClick: (mangaId: String, chapterId: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ShelfViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    ShelfScreen(
        state = state,
        onMangaClick = onMangaClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ShelfScreen(
    state: ShelfState,
    onMangaClick: (mangaId: String, chapterId: String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val view = LocalView.current
    val isDarkTheme = isSystemInDarkTheme()

    // Set status bar icons based on theme: dark icons for light theme, light icons for dark theme
    DisposableEffect(isDarkTheme) {
        val window = (view.context as Activity).window
        val windowInsetsController = WindowCompat.getInsetsController(window, view)
        // isAppearanceLightStatusBars = true means dark icons (for light backgrounds)
        // isAppearanceLightStatusBars = false means light icons (for dark backgrounds)
        windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
        onDispose { }
    }

    with(animatedVisibilityScope) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
        ) {
            // Top bar with fade animation during screen transitions
            ShelfTopBar(
                onSettingsClick = { /* TODO: Navigate to settings */ },
                modifier = Modifier.animateEnterExit(
                    enter = fadeIn(),
                    exit = fadeOut()
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.mangaList, key = { it.id }) { manga ->
                    MangaCoverCard(
                        manga = manga,
                        onClick = {
                            val firstChapter = manga.chapters.firstOrNull()
                            if (firstChapter != null) {
                                onMangaClick(manga.id, firstChapter.id)
                            }
                        },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                }
            }
        }
    }
}
