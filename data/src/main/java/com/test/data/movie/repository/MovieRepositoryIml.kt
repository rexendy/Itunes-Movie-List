package com.test.data.movie.repository

import com.test.data.core.ApiService
import com.test.data.movie.mapper.MovieMapper
import com.test.domain.core.RequestParamMapper
import com.test.domain.movie.repository.MovieRepository
import com.test.domain.movie.usecase.GetMoviesResult
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryIml @Inject constructor(
    private val api: ApiService,
    private val mapper: MovieMapper
): MovieRepository{
    override fun getMovies(term: String, country: String, media: String): Single<GetMoviesResult> {
        return api.getMovies(
            options = RequestParamMapper().apply {
                add("term" to term)
                add("country" to country)
                add("media" to media)
            }.toBody()
        )
            .map { response ->
                if (response.isSuccessful) {
                    response.body()?.let { movieRes ->
                        return@map GetMoviesResult.OnSuccess(movieRes.results.map { movieEntity ->
                            mapper.mapFromEntity(movieEntity)
                        })
                    }
                    GetMoviesResult.UnknownError
                } else {
                    GetMoviesResult.UnknownError
                }
            }
        }
}