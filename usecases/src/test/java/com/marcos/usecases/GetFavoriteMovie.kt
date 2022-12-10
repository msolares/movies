package com.marcos.usecases

import com.marcos.data.repository.MoviesRepository
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.GetFavoriteMovie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@RunWith(MockitoJUnitRunner::class)
class GetFavoriteMovieTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository
    lateinit var getFavoriteMovie: GetFavoriteMovie

    @Before
    fun setUp(){
        getFavoriteMovie = GetFavoriteMovie(moviesRepository)
    }

    @Test
    fun `invoke calls favorite movies repository` (){
        runBlocking {

            val movies = listOf(mockedMovie.copy(favorite = true))
            whenever(moviesRepository.getFavoritesMovies()).thenReturn(movies)

            val result = getFavoriteMovie.invoke()

            Assert.assertEquals(movies, result)
        }
    }



}