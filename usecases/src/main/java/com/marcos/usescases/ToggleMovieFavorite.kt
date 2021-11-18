package com.marcos.usescases

import com.marcos.data.repository.MoviesRepository
import com.marcos.domain.Movie

class ToggleMovieFavorite (private val moviesRepository: MoviesRepository) {
    suspend fun invoke(movie: Movie): Movie = with(movie) {
        copy(favorite = !favorite).also { moviesRepository.update(it) }
    }
}