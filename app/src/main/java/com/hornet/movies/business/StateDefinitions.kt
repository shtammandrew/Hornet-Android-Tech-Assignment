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

sealed interface UserAction {
    data object LoadInitial : UserAction
    data object LoadMore : UserAction
    data class ToggleExpand(val movieId: Int) : UserAction
    data class ShowPoster(val url: String?) : UserAction
    data object DismissPoster : UserAction
    data class SelectGenre(val genreId: Int?) : UserAction
}

sealed interface InternalAction {
    data class MoviesLoaded(val newMovies: List<Movie>) : InternalAction
    data class MovieDetailsLoaded(val movieId: Int, val details: MovieDetails) : InternalAction
    data class GenresLoaded(val genres: List<Genre>) : InternalAction
}

sealed interface SideEffect {
    data object FetchNextMovies : SideEffect
    data object FetchGenres : SideEffect
    data class FetchMovieDetails(val movieId: Int) : SideEffect
}

/** State of the ui screen */
data class ViewState(
    val movies: List<MovieUi>,
    val genreStats: List<GenreStat>,
    val selectedPoster: String?,
    val isLoading: Boolean
)