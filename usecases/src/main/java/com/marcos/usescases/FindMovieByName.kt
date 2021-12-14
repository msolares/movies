package com.marcos.usescases

import com.marcos.data.repository.MoviesRepository
import com.marcos.domain.Movie

class FindMovieByName (private val moviesRepository: MoviesRepository) {
    suspend fun invoke(name: String): Movie = moviesRepository.findByName(name)
}