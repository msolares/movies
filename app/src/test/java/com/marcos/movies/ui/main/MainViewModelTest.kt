package com.marcos.movies.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.FindMovieByName
import com.marcos.usescases.GetPopularMovies
import com.marcos.usescases.GetFavoriteMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPopularMovies: GetPopularMovies

    @Mock
    lateinit var getFavoriteMovie: GetFavoriteMovie

    @Mock
    lateinit var findMoviesByName: FindMovieByName

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getPopularMovies,  findMoviesByName,  Dispatchers.Unconfined, getFavoriteMovie)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        vm.model.observeForever(observer)

        verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)
            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `after requesting the permission, getPopularMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(id = 1))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Content(movies))
        }
    }

    @Test
    fun `after requesting the permission, getFavoritesMovies is called`() {

        runBlocking {
            val movies = listOf(mockedMovie.copy(favorite = true))
            whenever(getFavoriteMovie.invoke()).thenReturn(movies)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Content(movies))
        }
    }

    @Test
    fun `after requesting the permission, findMoviesByName is called`(nameMovie: String) {

        runBlocking {
            val movies = listOf(mockedMovie.copy(title = nameMovie))
            whenever(getPopularMovies.invoke()).thenReturn(movies)

            vm.model.observeForever(observer)

            vm.onCoarsePermissionRequested()

            verify(observer).onChanged(MainViewModel.UiModel.Content(movies))
        }
    }

}