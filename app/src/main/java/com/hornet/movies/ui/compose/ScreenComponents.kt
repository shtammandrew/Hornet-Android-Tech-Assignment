package com.hornet.movies.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hornet.movies.ui.model.MovieUi

@Composable
fun PosterDialog(posterUrl: String?, onDismiss: () -> Unit) {
    if (posterUrl != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA000000))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f)
            )
        }
    }
}

@Composable
fun MovieItem(item: MovieUi, onToggleExpand: () -> Unit, onPosterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (item.highlight) Color(0xFFE0F7FA) else Color.White)
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.movie.poster,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clickable { onPosterClick() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onToggleExpand() }
            ) {
                Text(item.movie.title, style = MaterialTheme.typography.titleMedium)
                Text(item.movie.overview, maxLines = 3, style = MaterialTheme.typography.bodySmall)
                Text("Rating: ${item.movie.vote_average}", style = MaterialTheme.typography.bodySmall)
            }
        }
        if (item.expanded && item.details != null) {
            val director = item.details.director?.name ?: "N/A"
            val actors = item.details.actors
                .take(3)
                .joinToString(", ") { it.name }
            val company = item.details.production_company?.name ?: "N/A"
            Column {
                Text("Director: $director", style = MaterialTheme.typography.bodySmall)
                Text("Actors: $actors", style = MaterialTheme.typography.bodySmall)
                Text("Company: $company", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}