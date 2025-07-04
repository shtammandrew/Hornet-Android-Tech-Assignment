package com.hornet.movies.ui.compose.v2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hornet.movies.ui.model.MovieUi

@Composable
fun MovieItemV2(item: MovieUi, onToggleExpand: () -> Unit, onPosterClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val bgColor = if (item.highlight)
        colors.secondary.copy(alpha = 0.2f)
    else
        colors.surface

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(bgColor, shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = item.movie.poster,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onPosterClick() }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onToggleExpand() }
                ) {
                    Text(
                        text = item.movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onSurface // Automatically white in dark mode, black in light
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        item.movie.overview,
                        maxLines = 3,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Rating: ${item.movie.vote_average}",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.primary
                    )
                }
            }

            if (item.expanded && item.details != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.padding(start = 4.dp)) {
                    Text(
                        "Director: ${item.details.director?.name ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurface
                    )
                    Text(
                        "Actors: ${item.details.actors.take(3).joinToString(", ") { it.name }}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurface
                    )
                    Text(
                        "Company: ${item.details.productionCompany?.name ?: "N/A"}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun PosterDialogV2(posterUrl: String?, onDismiss: () -> Unit) {
    if (posterUrl != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.85f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Transparent)
                    .padding(12.dp)
            )
        }
    }
}