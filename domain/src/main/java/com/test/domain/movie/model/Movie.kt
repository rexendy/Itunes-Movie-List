package com.test.domain.movie.model

data class Movie(
    val trackId: Long,
    val artistName: String,
    val trackName: String?,
    val artworkUrl100: String?,
    val primaryGenreName: String?,
    val trackPrice: Double,
    val longDescription: String?,
    val currency: String?
)