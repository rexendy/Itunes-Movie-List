package com.test.movieapp.movie.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.domain.movie.usecase.GetMoviesUseCase
import com.test.domain.session.usecase.GetSessionUseCase
import com.test.domain.session.usecase.SaveSessionUseCase

class MovieViewModelFactory(
    private val getMovies: GetMoviesUseCase,
    private val saveSession: SaveSessionUseCase,
    private val getSession: GetSessionUseCase
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(getMovies, saveSession, getSession) as T
    }
}