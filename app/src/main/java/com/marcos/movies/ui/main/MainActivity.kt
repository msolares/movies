package com.marcos.movies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcos.movies.databinding.ActivityMainBinding
import com.marcos.movies.models.Movie
import com.marcos.movies.models.MoviesRepository
import com.marcos.movies.ui.common.startActivity
import com.marcos.movies.ui.detail.DetailActivity

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val mainPresenter by lazy { MainPresenter(MoviesRepository(this))}

    private val adapter = MoviesAdapter {
        mainPresenter.onMovieClicked(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainPresenter.onCreate(this)
        binding.recycler.adapter = adapter


    }

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun updateData(movies: List<Movie>) {
        adapter.movies = movies
    }

    override fun navigateTo(movie: Movie) {
       startActivity<DetailActivity>{
           putExtra(DetailActivity.MOVIE, movie)
       }
    }
}