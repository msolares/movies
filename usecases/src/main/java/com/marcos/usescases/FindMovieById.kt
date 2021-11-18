package com.marcos.usescases

import com.marcos.data.repository.MoviesRepository
import com.marcos.domain.Movie

class FindMovieById (private val moviesRepository: MoviesRepository) {
    suspend fun invoke(id: Int): Movie = moviesRepository.findById(id)
}