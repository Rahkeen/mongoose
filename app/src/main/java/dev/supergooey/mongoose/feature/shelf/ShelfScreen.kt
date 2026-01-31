package dev.supergooey.mongoose.feature.shelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.supergooey.mongoose.models.Manga

@Composable
fun ShelfRoute(
    onMangaClick: (mangaId: String, chapterId: String) -> Unit,
    viewModel: ShelfViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    ShelfScreen(
        state = state,
        onMangaClick = onMangaClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelfScreen(
    state: ShelfState,
    onMangaClick: (mangaId: String, chapterId: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manga Shelf") }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(state.mangaList, key = { it.id }) { manga ->
                MangaCoverCard(
                    manga = manga,
                    onClick = {
                        val firstChapter = manga.chapters.firstOrNull()
                        if (firstChapter != null) {
                            onMangaClick(manga.id, firstChapter.id)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MangaCoverCard(
    manga: Manga,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column {
            Image(
                painter = painterResource(id = manga.coverResourceId),
                contentDescription = manga.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f)
            )
            Text(
                text = manga.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
