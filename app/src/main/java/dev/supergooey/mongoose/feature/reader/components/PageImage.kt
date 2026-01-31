package dev.supergooey.mongoose.feature.reader.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dev.supergooey.mongoose.models.Page
import dev.supergooey.mongoose.models.PageImageSource

@Composable
fun PageImage(
    page: Page,
    modifier: Modifier = Modifier
) {
    when (val source = page.imageSource) {
        is PageImageSource.LocalDrawable -> {
            Image(
                painter = painterResource(id = source.resourceId),
                contentDescription = "Page ${page.index + 1}",
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxSize()
            )
        }
        is PageImageSource.RemoteUrl -> {
            // Future: Use Coil for remote image loading
            // AsyncImage(
            //     model = source.url,
            //     contentDescription = "Page ${page.index + 1}",
            //     contentScale = ContentScale.Fit,
            //     modifier = modifier.fillMaxSize()
            // )
        }
    }
}
