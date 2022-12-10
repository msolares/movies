package com.marcos.movies.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.marcos.movies.R
import com.marcos.movies.ui.PermissionRequester
import com.marcos.movies.databinding.ActivityMainBinding
import com.marcos.movies.ui.common.startActivity
import com.marcos.movies.ui.detail.DetailActivity
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MoviesAdapter
    private val coarsePermissionRequester =
            PermissionRequester(this, ACCESS_COARSE_LOCATION)

    private val viewModel: MainViewModel by viewModel()
    private var favorite:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MoviesAdapter(viewModel::onMovieClicked)
        binding.recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))

        binding.search.setOnQueryTextListener(this)
        binding.movieMainFavorite.setOnClickListener{viewModel.clickedFavorite(favorite)}
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return if (query != null) {
            viewModel.onSearch(query)
            true
        }else
            false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return if (newText != null) {
            viewModel.onSearch(newText)
            true
        }else{
            false
        }
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        when (model) {
            is MainViewModel.UiModel.Content -> updateFavorite(model)
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(DetailActivity.MOVIE, model.movie.id)
            }
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        }
        val icon = if (!favorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        binding.movieMainFavorite.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, icon))
    }

    private fun updateFavorite(model: MainViewModel.UiModel.Content) {
        adapter.movies = model.movies
        favorite = !favorite
    }
}