package com.hornet.movies.ui.model

import com.hornet.movies.data.model.meta.Genre
import com.hornet.movies.data.model.movie.Movie
import com.hornet.movies.data.model.movie.MovieDetails

data class MovieUi(
    val movie: Movie,
    val expanded: Boolean,
    val details: MovieDetails?,
    val highlight: Boolean
)

data class GenreStat(
    val genre: Genre,
    val count: Int,
    val selected: Boolean
)