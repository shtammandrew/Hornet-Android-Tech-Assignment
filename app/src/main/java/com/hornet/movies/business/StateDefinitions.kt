package com.hornet.movies.business

import com.hornet.movies.data.model.meta.Genre
import com.hornet.movies.data.model.movie.Movie
import com.hornet.movies.data.model.movie.MovieDetails

/** Simple state machine will be used to
 * support ELM-based approach. Here the definitions can found */

data class StateMachineState(
    val movies: List<Movie> = emptyList(),
    val expanded: Set<Int> = emptySet(),
    val details: Map<Int, MovieDetails> = emptyMap(),
    val selectedPoster: String? = null,
    val genres: List<Genre> = emptyList(),
    val selectedGenre: Int? = null,
    val isLoading: Boolean = false,
    val reachedEnd: Boolean = false,
    val currentPage: Int = 1
)