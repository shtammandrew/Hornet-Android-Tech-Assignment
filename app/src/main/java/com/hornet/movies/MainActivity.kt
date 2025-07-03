package com.hornet.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hornet.movies.business.MovieViewModel
import com.hornet.movies.business.UserAction
import com.hornet.movies.ui.compose.rememberViewState
import com.hornet.movies.ui.compose.v2.MovieListV2
import com.hornet.movies.ui.compose.v2.PosterDialogV2
import com.hornet.movies.ui.theme.HornetMoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: MovieViewModel = viewModel()
            val state by vm.state.collectAsState()
            val viewState = rememberViewState(state)
            HornetMoviesTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    MovieListV2(viewState, onAction = vm::send)
                    PosterDialogV2(viewState.selectedPoster) { vm.send(UserAction.DismissPoster) }
                }
            }
        }
    }
}