package com.hornet.movies.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hornet.movies.data.MoviesRepo
import com.hornet.movies.data.MoviesRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val moviesRepo: MoviesRepo = MoviesRepoImpl()

    private val _state = MutableStateFlow(StateMachineState())
    val state: StateFlow<StateMachineState> = _state

    init { send(UserAction.LoadInitial) }

    fun send(action: UserAction) {
        val (newState, effects) = reduce(_state.value, action)
        _state.value = newState
        effects.forEach(::handle)
    }

    //Side effects
    private fun handle(effect: SideEffect) {
        viewModelScope.launch(Dispatchers.IO) {
            when (effect) {
                is SideEffect.FetchNextMovies -> {
                    val result = moviesRepo.pageOfMovies(_state.value.currentPage).results.filter { it.vote_average >= 7 }
                    sendInternal(InternalAction.MoviesLoaded(result))
                }
                is SideEffect.FetchGenres -> {
                    sendInternal(InternalAction.GenresLoaded(moviesRepo.movieGenres().genres))
                }
                is SideEffect.FetchMovieDetails -> {
                    sendInternal(InternalAction.MovieDetailsLoaded(effect.movieId, moviesRepo.movieDetails(effect.movieId)))
                }
            }
        }
    }

    // Reducer
    private fun reduce(state: StateMachineState, action: Any): Pair<StateMachineState, List<SideEffect>> {
        return when (action) {
            is UserAction.LoadInitial -> state.copy(isLoading = true) to listOf(SideEffect.FetchNextMovies, SideEffect.FetchGenres)
            is UserAction.LoadMore -> if (state.isLoading || state.reachedEnd) state to emptyList() else state.copy(isLoading = true) to listOf(SideEffect.FetchNextMovies)
            is UserAction.ToggleExpand -> {
                val expanded = state.expanded.toMutableSet().apply {
                    if (contains(action.movieId)) remove(action.movieId) else add(action.movieId)
                }
                val fx = if (state.details.containsKey(action.movieId)) emptyList() else listOf(SideEffect.FetchMovieDetails(action.movieId))
                state.copy(expanded = expanded) to fx
            }
            is UserAction.ShowPoster -> state.copy(selectedPoster = action.url) to emptyList()
            is UserAction.DismissPoster -> state.copy(selectedPoster = null) to emptyList()
            is UserAction.SelectGenre -> {
                val newGenre = if (action.genreId == state.selectedGenre) null else action.genreId
                state.copy(selectedGenre = newGenre) to emptyList()
            }
            is InternalAction.MoviesLoaded -> {
                val updated = state.movies + action.newMovies
                val stop = action.newMovies.none { it.vote_average >= 7 }
                state.copy(movies = updated, currentPage = state.currentPage + 1, isLoading = false, reachedEnd = stop) to emptyList()
            }
            is InternalAction.MovieDetailsLoaded -> state.copy(details = state.details + (action.movieId to action.details)) to emptyList()
            is InternalAction.GenresLoaded -> state.copy(genres = action.genres) to emptyList()
            else -> state to emptyList()
        }
    }

    private fun sendInternal(action: InternalAction) {
        val (newState, _) = reduce(_state.value, action)
        _state.value = newState
    }
}