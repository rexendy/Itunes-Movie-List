package com.test.movieapp.movie.event

import com.test.domain.movie.model.Movie
import com.test.movieapp.utils.LiveEvent

/**
 * LiveEvent notification for showing movie detail page
 * */
object GoToMovieDetail: LiveEvent<Movie?>()