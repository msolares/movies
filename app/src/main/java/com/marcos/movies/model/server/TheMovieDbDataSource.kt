package com.marcos.movies.model.server

import com.marcos.data.source.RemoteDataSource
import com.marcos.domain.Movie
import com.marcos.movies.model.toDomainMovie

class TheMovieDbDataSource (private val theMovieDb: TheMovieDb) : RemoteDataSource {

    override suspend fun getPopularMovies(apiKey: String, region: String): List<Movie> =
        theMovieDb.service
            .listPopularMoviesAsync(apiKey, region)
            .results
            .map { it.toDomainMovie() }

    override suspend fun getFindMoviesByName(
        name: String,
        apiKey: String,
        region: String
    ): List<Movie> = theMovieDb.service.listSearchMoviesAsync(apiKey, region, name).results.map { it.toDomainMovie() }

}