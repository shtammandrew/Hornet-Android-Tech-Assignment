package com.hornet.movies.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

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