package dev.supergooey.mongoose.data

import dev.supergooey.mongoose.R
import dev.supergooey.mongoose.models.Chapter
import dev.supergooey.mongoose.models.Manga
import dev.supergooey.mongoose.models.Page
import dev.supergooey.mongoose.models.PageImageSource

object SampleMangaData {
    private val sampleChapter = Chapter(
        id = "chapter-1",
        mangaId = "manga-1",
        number = 1,
        title = "Chapter 1",
        pages = listOf(
            Page(
                id = "page-0",
                chapterId = "chapter-1",
                index = 0,
                imageSource = PageImageSource.LocalDrawable(R.drawable.page_0)
            ),
            Page(
                id = "page-1",
                chapterId = "chapter-1",
                index = 1,
                imageSource = PageImageSource.LocalDrawable(R.drawable.page_1)
            ),
            Page(
                id = "page-2",
                chapterId = "chapter-1",
                index = 2,
                imageSource = PageImageSource.LocalDrawable(R.drawable.page_2)
            )
        )
    )

    private val sampleManga = Manga(
        id = "manga-1",
        title = "Sample Manga",
        coverResourceId = R.drawable.cover,
        chapters = listOf(sampleChapter)
    )

    val allManga: List<Manga> = listOf(sampleManga)

    fun getMangaById(id: String): Manga? = allManga.find { it.id == id }

    fun getChapterById(mangaId: String, chapterId: String): Chapter? {
        return getMangaById(mangaId)?.chapters?.find { it.id == chapterId }
    }
}
