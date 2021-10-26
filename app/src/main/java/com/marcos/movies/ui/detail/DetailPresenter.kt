package com.marcos.movies.ui.detail

import com.marcos.movies.models.Movie

class DetailPresenter {
    private var view: View? = null

    interface View {
        fun updateUi (movie: Movie)
    }

    fun onCreate (view: View, movie: Movie){
        this.view = view
        view.updateUi(movie)
    }

    fun onDestroy() {
        view = null
   }

}