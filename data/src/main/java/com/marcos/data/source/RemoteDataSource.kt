package com.marcos.data.source

import com.marcos.domain.Movie


interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
}