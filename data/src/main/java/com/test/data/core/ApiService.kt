package com.test.data.core

import com.test.data.movie.response.GetMovieResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface ApiService {
    @GET("search")
    fun getMovies(@QueryMap options: Map<String, Any>): Single<Response<GetMovieResponse>>
}