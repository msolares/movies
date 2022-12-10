package com.marcos.movies.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.marcos.data.source.LocalDataSource
import com.marcos.movies.ui.FakeLocalDataSource
import com.marcos.movies.ui.defaultFakeMovies
import com.marcos.movies.ui.initMockedDi
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.FindMovieByName
import com.marcos.usescases.GetFavoriteMovie
import com.marcos.usescases.GetPopularMovies
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.koin.test.get

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get(), get(), get()) }
            factory { GetPopularMovies(get()) }
            factory { FindMovieByName(get()) }
            factory { GetFavoriteMovie(get()) }
        }
        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server when local source is empty`() {
        vm.model.observeForever(observer)
        vm.onCoarsePermissionRequested()
        verify(observer).onChanged(MainViewModel.UiModel.Content(defaultFakeMovies))
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalMovies = listOf(mockedMovie.copy(10), mockedMovie.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.movies = fakeLocalMovies
        vm.model.observeForever(observer)
        vm.onCoarsePermissionRequested()
        verify(observer).onChanged(MainViewModel.UiModel.Content(fakeLocalMovies))
    }
}