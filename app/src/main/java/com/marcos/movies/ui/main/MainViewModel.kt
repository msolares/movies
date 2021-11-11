package com.marcos.movies.ui.main

import androidx.lifecycle.*
import com.marcos.movies.model.server.Movie
import com.marcos.movies.model.server.MoviesRepository
import com.marcos.movies.ui.common.Event
import com.marcos.movies.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel(), Scope by Scope.Impl(){

    private val _navigation = MutableLiveData<Event<Movie>> ()
    val navigation: LiveData<Event<Movie>> = _navigation

    sealed class UiModel{
        object Loading : UiModel()
        class  Content(val movie: List<com.antonioleiva.mymovies.model.database.Movie>): UiModel()
        object RequestLocationPermission : UiModel()
    }



    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel>
            get() {
                if (_model.value == null){
                    refresh()
                }
                return _model
            }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.RequestLocationPermission
        }
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(moviesRepository.findPopularMovies())
        }
    }

    fun onMovieClicked(movie: Movie){
        _navigation.value = Event(movie)
    }

    override fun onCleared() {
        cancelScope()
    }

}