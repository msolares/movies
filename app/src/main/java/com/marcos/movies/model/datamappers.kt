package com.marcos.movies.model

import com.marcos.domain.Movie
import com.marcos.movies.model.server.Movie as ServerMovie

fun Movie.toRoomMovie() : com.marcos.movies.model.database.Movie =
    com.marcos.movies.model.database.Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
    )

fun com.marcos.movies.model.database.Movie.toDomainMovie() : Movie = Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        favorite
)

fun ServerMovie.toDomainMovie(): Movie =
        Movie(
                0,
                title,
                overview,
                releaseDate,
                posterPath,
                backdropPath ?: posterPath,
                originalLanguage,
                originalTitle,
                popularity,
                voteAverage,
                false
        )