package com.hornet.movies.ui.compose.v2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hornet.movies.business.UserAction
import com.hornet.movies.business.ViewState
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MovieListV2(viewState: ViewState, onAction: (UserAction) -> Unit) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= listState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore }.collectLatest { load ->
            if (load) onAction(UserAction.LoadMore)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = listState
    ) {
        stickyHeader {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            ) {
                items(viewState.genreStats, key = { it.genre.id }) { genreStat ->
                    FilterChip(
                        selected = genreStat.selected,
                        onClick = {
                            onAction(
                                UserAction.SelectGenre(
                                    if (genreStat.genre.id == 0) null else genreStat.genre.id
                                )
                            )
                        },
                        label = {
                            Text(
                                "${genreStat.genre.name} (${genreStat.count})",
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        items(viewState.movies, key = { it.movie.id }) { item ->
            MovieItemV2(
                item = item,
                onToggleExpand = { onAction(UserAction.ToggleExpand(item.movie.id)) },
                onPosterClick = { onAction(UserAction.ShowPoster(item.movie.poster)) }
            )
        }

        item {
            if (viewState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}