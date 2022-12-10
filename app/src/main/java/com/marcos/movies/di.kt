package com.marcos.movies

import android.app.Application
import com.marcos.movies.model.database.MovieDatabase
import com.marcos.data.repository.MoviesRepository
import com.marcos.data.repository.PermissionChecker
import com.marcos.data.repository.RegionRepository
import com.marcos.data.source.LocalDataSource
import com.marcos.data.source.LocationDataSource
import com.marcos.data.source.RemoteDataSource
import com.marcos.movies.model.AndroidPermissionChecker
import com.marcos.movies.model.PlayServicesLocationDataSource
import com.marcos.movies.model.database.RoomDataSource
import com.marcos.movies.model.server.TheMovieDb
import com.marcos.movies.model.server.TheMovieDbDataSource
import com.marcos.movies.ui.detail.DetailActivity
import com.marcos.movies.ui.detail.DetailViewModel
import com.marcos.movies.ui.main.MainActivity
import com.marcos.movies.ui.main.MainViewModel
import com.marcos.usescases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    factory<RemoteDataSource> { TheMovieDbDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.themoviedb.org/3/" }
    single { TheMovieDb(get(named("baseUrl"))) }
}

private val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { MoviesRepository(get(), get(), get(named("apiKey")), get())}
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get(), get(), get()) }
        scoped { GetPopularMovies(get()) }
        scoped { FindMovieByName(get()) }
        scoped { GetFavoriteMovie(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
        scoped { FindMovieById(get()) }
        scoped { ToggleMovieFavorite(get()) }
    }
}