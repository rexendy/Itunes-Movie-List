package com.test.domain.movie.repository

import com.test.domain.movie.usecase.GetMoviesResult
import io.reactivex.Single

/**
 * MovieRepository
 * data is from remote source
 */
interface MovieRepository {
    /**
     * get movies from remote source
     * @return list of movies
     */
    fun getMovies(term: String, country: String, media: String): Single<GetMoviesResult>
}