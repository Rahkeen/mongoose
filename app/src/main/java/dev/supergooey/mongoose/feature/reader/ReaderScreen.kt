package dev.supergooey.mongoose.feature.reader

import android.app.Activity
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.supergooey.mongoose.MongooseApplication
import dev.supergooey.mongoose.feature.reader.components.PageImage
import dev.supergooey.mongoose.feature.reader.components.ReaderOverlay
import dev.supergooey.mongoose.settings.ReadingDirection

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ReaderRoute(
    mangaId: String,
    chapterId: String,
    startPage: Int,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ReaderViewModel = viewModel(
        factory = ReaderViewModel.Factory(
            mangaId = mangaId,
            chapterId = chapterId,
            startPage = startPage,
            readerSettings = (LocalContext.current.applicationContext as MongooseApplication).readerSettings
        )
    )
) {
    val state by viewModel.state.collectAsState()

    ReaderScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        mangaId = mangaId
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ReaderScreen(
    state: ReaderState,
    onAction: (ReaderAction) -> Unit,
    onBackClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    mangaId: String
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val windowInsetsController = WindowCompat.getInsetsController(window, view)

    // Set light status bar icons (white) for dark reader background
    DisposableEffect(Unit) {
        windowInsetsController.isAppearanceLightStatusBars = false
        onDispose {
            // Don't restore here - let the destination screen manage its own appearance
        }
    }

    with(sharedTransitionScope) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "manga-$mangaId"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
        ) {
            if (state.pages.isNotEmpty()) {
                val pagerState = rememberPagerState(
                    initialPage = state.currentPage,
                    pageCount = { state.pages.size }
                )

                // Sync pager state with view model
                LaunchedEffect(pagerState) {
                    snapshotFlow { pagerState.currentPage }
                        .collect { page ->
                            onAction(ReaderAction.PageChanged(page))
                        }
                }

                // Scroll to page when slider is used
                LaunchedEffect(state.currentPage) {
                    if (pagerState.currentPage != state.currentPage) {
                        pagerState.animateScrollToPage(state.currentPage)
                    }
                }

                when (state.readingDirection) {
                    ReadingDirection.Horizontal -> {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = { offset ->
                                            val centerStart = size.width * 0.3f
                                            val centerEnd = size.width * 0.7f
                                            if (offset.x in centerStart..centerEnd) {
                                                onAction(ReaderAction.ToggleOverlay)
                                            }
                                        }
                                    )
                                }
                        ) { page ->
                            PageImage(page = state.pages[page])
                        }
                    }
                    ReadingDirection.Vertical -> {
                        VerticalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = { offset ->
                                            val centerStart = size.height * 0.3f
                                            val centerEnd = size.height * 0.7f
                                            if (offset.y in centerStart..centerEnd) {
                                                onAction(ReaderAction.ToggleOverlay)
                                            }
                                        }
                                    )
                                }
                        ) { page ->
                            PageImage(page = state.pages[page])
                        }
                    }
                }
            }

            // Overlay controls
            ReaderOverlay(
                visible = state.showOverlay,
                chapterTitle = state.chapterTitle,
                currentPage = state.currentPage,
                totalPages = state.pages.size,
                readingDirection = state.readingDirection,
                onBackClick = onBackClick,
                onPageSliderChange = { page ->
                    onAction(ReaderAction.PageChanged(page))
                },
                onToggleDirection = {
                    val newDirection = if (state.readingDirection == ReadingDirection.Horizontal) {
                        ReadingDirection.Vertical
                    } else {
                        ReadingDirection.Horizontal
                    }
                    onAction(ReaderAction.SetReadingDirection(newDirection))
                }
            )
        }
    }
}
