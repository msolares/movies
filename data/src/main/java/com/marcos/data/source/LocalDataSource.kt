package com.marcos.data.source

import com.marcos.domain.Movie

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveMovies (movies: List<Movie>)
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getFavoritesMovies(): List<Movie>
    suspend fun findById(id: Int): Movie
    suspend fun findByName(name: String): List<Movie>
    suspend fun update(movie: Movie)
}