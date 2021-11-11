package com.marcos.movies.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcos.movies.model.server.Movie
import com.marcos.movies.model.server.MoviesRepository
import com.marcos.movies.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int, private val moviesRepository: MoviesRepository) :
        ScopedViewModel() {

    class UiModel(val movie: com.antonioleiva.mymovies.model.database.Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findMovie()
            return _model
        }

    private fun findMovie() = launch {
        _model.value = UiModel(moviesRepository.findById(movieId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.movie?.let {
            val updatedMovie = it.copy(favorite = !it.favorite)
            _model.value = UiModel(updatedMovie)
            moviesRepository.update(updatedMovie)
        }
    }
}