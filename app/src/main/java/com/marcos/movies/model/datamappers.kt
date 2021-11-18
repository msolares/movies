package com.marcos.movies.model

import com.marcos.domain.Movie

fun Movie.toRoomMovie() : com.antonioleiva.mymovies.model.database.Movie = com.antonioleiva.mymovies.model.database.Movie(
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

fun com.antonioleiva.mymovies.model.database.Movie.toDomainMovie() : Movie = Movie(
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

//fun ServerMovie.toDomainMovie(): Movie (
//
//)