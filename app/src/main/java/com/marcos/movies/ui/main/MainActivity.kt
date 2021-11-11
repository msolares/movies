package com.marcos.movies.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.antonioleiva.mymovies.PermissionRequester
import com.marcos.movies.databinding.ActivityMainBinding
import com.marcos.movies.model.server.MoviesRepository
import com.marcos.movies.ui.common.app
import com.marcos.movies.ui.common.getViewModel
import com.marcos.movies.ui.common.startActivity
import com.marcos.movies.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = getViewModel { MainViewModel(MoviesRepository(app)) }



        adapter = MoviesAdapter(viewModel::onMovieClicked)
        recycler.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))

        viewModel.navigation.observe(this, Observer { event ->
            event.getContentIfNotHandled().let {
                startActivity<DetailActivity>{
                    putExtra(DetailActivity.MOVIE, it)
                }
            }
        })

    }

    fun updateUi(model: MainViewModel.UiModel){
//        progress.visibility == if (model == MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when (model){
            is MainViewModel.UiModel.Content -> adapter.movies = model.movie
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
    }



}