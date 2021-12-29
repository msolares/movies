package com.marcos.data.repository

import com.marcos.data.source.LocalDataSource
import com.marcos.data.source.RemoteDataSource
import com.marcos.domain.Movie

class MoviesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String,
    private val regionRepository: RegionRepository
    ) {
    suspend fun getPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.getPopularMovies()
    }

    suspend fun getFindByNameMovies(name: String): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getFindMoviesByName(name, apiKey, regionRepository.findLastRegion())
            localDataSource.saveMovies(movies)
        }
        return localDataSource.findByName(name)
    }

    suspend fun findById(id: Int): Movie = localDataSource.findById(id)
    suspend fun update(movie: Movie) = localDataSource.update(movie)
}


