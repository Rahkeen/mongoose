package dev.supergooey.mongoose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.supergooey.mongoose.feature.reader.ReaderRoute as ReaderScreen
import dev.supergooey.mongoose.feature.shelf.ShelfRoute as ShelfScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ShelfRoute
    ) {
        composable<ShelfRoute> {
            ShelfScreen(
                onMangaClick = { mangaId, chapterId ->
                    navController.navigate(ReaderRoute(mangaId, chapterId))
                }
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
                }
            )
        }
    }
}
