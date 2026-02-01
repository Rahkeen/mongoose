package dev.supergooey.mongoose.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.supergooey.mongoose.feature.reader.ReaderRoute as ReaderScreen
import dev.supergooey.mongoose.feature.shelf.ShelfRoute as ShelfScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = ShelfRoute
            ) {
            composable<ShelfRoute> {
                ShelfScreen(
                    onMangaClick = { mangaId, chapterId ->
                        navController.navigate(ReaderRoute(mangaId, chapterId))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable
                )
            }

            composable<ReaderRoute> { backStackEntry ->
                val route: ReaderRoute = backStackEntry.toRoute()
                ReaderScreen(
                    mangaId = route.mangaId,
                    chapterId = route.chapterId,
                    startPage = route.startPage,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable
                )
            }
        }
        }
    }
}
