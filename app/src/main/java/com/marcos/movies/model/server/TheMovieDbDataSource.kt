package com.marcos.movies.model.server

import com.marcos.data.source.RemoteDataSource
import com.marcos.domain.Movie
import com.marcos.movies.model.toDomainMovie

class TheMovieDbDataSource : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        TheMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie()
            }

    override suspend fun getFindMoviesByName(
        name: String,
        apiKey: String,
        region: String
    ): List<Movie> {
        TODO("Not yet implemented")
    }
}