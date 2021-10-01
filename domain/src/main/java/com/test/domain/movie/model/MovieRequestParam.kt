package com.test.domain.movie.model

data class MovieRequestParam(
    var term: String = "",
    var country: String = "",
    var media: String = ""
    )