package com.marcos.movies.ui.main

import com.antonioleiva.mymovies.ui.common.Scope
import com.marcos.movies.models.Movie
import com.marcos.movies.models.MoviesRepository
import com.marcos.movies.ui.common.Scope
import kotlinx.coroutines.launch

class MainPresenter(private val moviesRepository: MoviesRepository) : Scope by Scope.Impl(){


    interface  View {
        fun showProgress()
        fun hideProgress()
        fun updateData (movies: List<Movie>)
        fun navigateTo (movie: Movie)
    }

    private var view: View? = null

    fun onCreate(view: View) {
        this.view = view
        initScope()
        launch {
            view.showProgress()
            view.updateData(moviesRepository.findPopularMovies().results)
            view.hideProgress()
        }
    }

    fun onDestroy() {
        this.view = null
        cancelScope()
    }

    fun onMovieClicked(movie: Movie){
        view?.navigateTo(movie)
    }
}