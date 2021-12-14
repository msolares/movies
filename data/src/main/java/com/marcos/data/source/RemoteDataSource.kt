package com.marcos.data.source

import com.marcos.domain.Movie


interface RemoteDataSource {
    suspend fun getPopularMovies(apiKey: String, region: String): List<Movie>
    suspend fun getFindMoviesByName(name:String, apiKey: String, region: String): List<Movie>
}

