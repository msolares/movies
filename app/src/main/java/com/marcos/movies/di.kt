package com.marcos.movies

import android.app.Application
import com.antonioleiva.mymovies.model.database.MovieDatabase
import com.marcos.data.repository.MoviesRepository
import com.marcos.data.repository.PermissionChecker
import com.marcos.data.repository.RegionRepository
import com.marcos.data.source.LocalDataSource
import com.marcos.data.source.LocationDataSource
import com.marcos.data.source.RemoteDataSource
import com.marcos.movies.model.AndroidPermissionChecker
import com.marcos.movies.model.PlayServicesLocationDataSource
import com.marcos.movies.model.database.RoomDataSource
import com.marcos.movies.model.server.TheMovieDbDataSource
import com.marcos.movies.ui.detail.DetailActivity
import com.marcos.movies.ui.detail.DetailViewModel
import com.marcos.movies.ui.main.MainActivity
import com.marcos.movies.ui.main.MainViewModel
import com.marcos.usescases.FindMovieById
import com.marcos.usescases.GetPopularMovies
import com.marcos.usescases.ToggleMovieFavorite
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { MovieDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { TheMovieDbDataSource() }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetPopularMovies(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}