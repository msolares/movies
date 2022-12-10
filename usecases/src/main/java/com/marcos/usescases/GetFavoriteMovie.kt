package com.marcos.usescases

import com.marcos.data.repository.MoviesRepository
import com.marcos.domain.Movie

class GetFavoriteMovie (private val moviesRepository: MoviesRepository){
    suspend fun invoke (): List<Movie> =
        moviesRepository.getFavoritesMovies()
}