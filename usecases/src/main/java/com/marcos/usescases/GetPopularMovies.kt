package com.marcos.usescases

import com.marcos.data.repository.MoviesRepository
import com.marcos.domain.Movie

class GetPopularMovies(private val moviesRepository: MoviesRepository) {

    suspend fun invoke (): List<Movie> =
        moviesRepository.getPopularMovies()


}