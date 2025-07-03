package com.hornet.movies.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.hornet.movies.business.StateMachineState
import com.hornet.movies.business.ViewState
import com.hornet.movies.data.model.meta.Genre
import com.hornet.movies.ui.model.GenreStat
import com.hornet.movies.ui.model.MovieUi


@Composable
fun rememberViewState(state: StateMachineState): ViewState {
    val movieUis = remember(state.movies, state.expanded, state.details, state.selectedGenre) {
        state.movies.map { m ->
            val isHighlighted = state.selectedGenre == null || m.genre_ids.contains(state.selectedGenre)
            MovieUi(m, state.expanded.contains(m.id), state.details[m.id], isHighlighted)
        }
    }
    val genreStats = remember(state.movies, state.genres, state.selectedGenre) {
        val genreCount = state.movies.flatMap { it.genre_ids }.groupingBy { it }.eachCount()
        buildList {
            add(GenreStat(Genre(0, "All"), state.movies.size, state.selectedGenre == null))
            addAll(state.genres.filter { genreCount.containsKey(it.id) }.map {
                GenreStat(it, genreCount[it.id] ?: 0, it.id == state.selectedGenre)
            })
        }
    }
    return ViewState(movieUis, genreStats, state.selectedPoster, state.isLoading)
}