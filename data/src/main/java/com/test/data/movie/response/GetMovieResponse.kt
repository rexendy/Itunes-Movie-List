package com.test.data.movie.response

import com.squareup.moshi.Json
import com.test.data.movie.model.MovieEntity

data class GetMovieResponse(
    @field:Json(name = "resultCount") val resultCount: Int,
    @field:Json(name = "results") val results: List<MovieEntity>
)
