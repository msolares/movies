package com.marcos.movies.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.antonioleiva.mymovies.ui.common.CoroutineScopeActivity
import com.marcos.movies.databinding.ActivityMainBinding
import com.marcos.movies.models.MoviesRepository
import com.marcos.movies.ui.common.startActivity
import com.marcos.movies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {
    private val moviesRepository: MoviesRepository by lazy { MoviesRepository(this) }

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(com.marcos.movies.ui.detail.DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter

        launch {
            adapter.movies = moviesRepository.findPopularMovies().results
        }
    }
}