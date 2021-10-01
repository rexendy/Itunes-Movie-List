package com.test.data.movie.mapper

import com.test.data.movie.model.MovieEntity
import com.test.domain.core.Mapper
import com.test.domain.movie.model.Movie
import javax.inject.Inject

/***
 * Map MovieEntity to and from Movie instance
 */
class MovieMapper @Inject constructor(): Mapper<MovieEntity, Movie> {
    override fun mapFromEntity(type: MovieEntity): Movie {
        return Movie(
            type.trackId,
            type.artistName,
            type.trackName,
            type.artworkUrl100,
            type.primaryGenreName,
            type.trackPrice,
            type.longDescription,
            type.currency
        )
    }

    override fun mapToEntity(type: Movie): MovieEntity {
        return MovieEntity(
            type.trackId,
            type.artistName,
            type.trackName,
            type.artworkUrl100,
            type.primaryGenreName,
            type.trackPrice,
            type.longDescription,
            type.currency
        )
    }
}