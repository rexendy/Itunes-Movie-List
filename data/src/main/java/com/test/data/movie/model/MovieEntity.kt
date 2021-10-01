package com.test.data.movie.model

import com.squareup.moshi.Json

data class MovieEntity(
    @field:Json(name = "trackId") var trackId: Long,
    @field:Json(name = "artistName") var artistName: String,
    @field:Json(name = "trackName") var trackName: String?,
    @field:Json(name = "artworkUrl100") var artworkUrl100: String?,
    @field:Json(name = "primaryGenreName") var primaryGenreName: String?,
    @field:Json(name = "trackPrice") var trackPrice: Double,
    @field:Json(name = "longDescription") var longDescription: String?,
    @field:Json(name = "currency") var currency: String?
)
