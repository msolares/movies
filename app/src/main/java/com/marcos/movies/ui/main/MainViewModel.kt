package com.marcos.movies.ui.main

import androidx.lifecycle.*
import com.marcos.domain.Movie
import com.marcos.movies.ui.common.ScopedViewModel
import com.marcos.usescases.FindMovieByName
import com.marcos.usescases.GetFavoriteMovie
import com.marcos.usescases.GetPopularMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(private val getPopularMovies: GetPopularMovies, private val findMoviesByName: FindMovieByName, uiDispatcher: CoroutineDispatcher, private val getFavoriteMovie: GetFavoriteMovie) : ScopedViewModel(uiDispatcher) {


    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
        class Navigation(val movie: Movie) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getPopularMovies.invoke())
        }
    }

    fun onMovieClicked(movie: Movie) {
        _model.value = UiModel.Navigation(movie)
    }

    fun onSearch(query: String) {
        launch {
            _model.value = UiModel.Content(findMoviesByName.invoke(query))
        }
    }

    fun clickedFavorite (favorite: Boolean){
        launch {
            if (favorite)
                _model.value = UiModel.Content(getFavoriteMovie.invoke())
            else
                _model.value = UiModel.Content(getPopularMovies.invoke())
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}