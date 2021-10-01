package com.test.domain.movie.usecase

import com.test.domain.core.SingleWithParamUseCase
import com.test.domain.movie.model.Movie
import com.test.domain.movie.model.MovieRequestParam
import com.test.domain.movie.repository.MovieRepository

class GetMoviesUseCase(
    private val repository: MovieRepository): SingleWithParamUseCase<MovieRequestParam,GetMoviesResult> {
    override fun execute(param: MovieRequestParam) = repository.getMovies(param.term, param.country, param.media)
}

sealed class GetMoviesResult {
    data class OnSuccess(val movies: List<Movie>) : GetMoviesResult()
    object UnknownError : GetMoviesResult()
}