package com.marcos.movies.model.server

import com.marcos.movies.model.server.MovieDbResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbService {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun listPopularMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDbResult

    @GET("/search/movie?include_adult=true")
    suspend fun listSearchMoviesAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("query") query: String
    ): MovieDbResult
}