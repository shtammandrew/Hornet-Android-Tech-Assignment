package com.hornet.movies.data

import com.hornet.movies.data.model.meta.Genres
import com.hornet.movies.data.model.movie.MovieDetails
import com.hornet.movies.data.model.movie.MovieResults

/** Despite only using the ApiService for movies,
 * this Repository is added to allow basic abstraction layer
 * for data control */

interface MoviesRepo {
    suspend fun pageOfMovies(pageNumber: Int): MovieResults
    suspend fun movieDetails(movieId: Int): MovieDetails
    suspend fun movieGenres(): Genres

}

class MoviesRepoImpl(private val moviesService: MoviesService = MoviesService.getInstance()) : MoviesRepo {
    override suspend fun pageOfMovies(pageNumber: Int): MovieResults {
        return moviesService.getTopMovies(page = pageNumber)
    }

    override suspend fun movieDetails(movieId: Int): MovieDetails {
        return moviesService.getMovieDetails(id = movieId)
    }

    override suspend fun movieGenres(): Genres {
        return moviesService.getGenres()
    }
}