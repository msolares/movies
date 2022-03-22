package com.marcos.usecases

import com.marcos.data.repository.MoviesRepository
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.ToggleMovieFavorite
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class ToggleMovieFavoriteTest {

    @Mock
    lateinit var moviesRepository: MoviesRepository

    lateinit var toggleMovieFavorite: ToggleMovieFavorite

    @Before
    fun setUp() {
        toggleMovieFavorite = ToggleMovieFavorite(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {

            val movie = mockedMovie.copy(id = 1)

            val result = toggleMovieFavorite.invoke(movie)

            verify(moviesRepository).update(result)
        }
    }

    @Test
    fun `favorite movie becomes unfavorite`() {
        runBlocking {

            val movie = mockedMovie.copy(favorite = true)

            val result = toggleMovieFavorite.invoke(movie)

            Assert.assertFalse(result.favorite)
        }
    }

    @Test
    fun `unfavorite movie becomes favorite`() {
        runBlocking {

            val movie = mockedMovie.copy(favorite = false)

            val result = toggleMovieFavorite.invoke(movie)

            Assert.assertTrue(result.favorite)
        }
    }

}