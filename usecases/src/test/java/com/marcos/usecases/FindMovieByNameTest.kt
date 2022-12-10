package com.marcos.usecases

import com.marcos.data.repository.MoviesRepository
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.FindMovieByName
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.whenever

class FindMovieByNameTest {

    lateinit var moviesRepository: MoviesRepository
    lateinit var findMovieByName: FindMovieByName

    @Before
    fun setUp() {
        findMovieByName = FindMovieByName(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {
            val movies = listOf(mockedMovie.copy(title = "prueba"))
            whenever(moviesRepository.getFindByNameMovies("prueba")).thenReturn(movies)

            val result = findMovieByName.invoke("prueba")

            Assert.assertEquals(movies, result)
        }
    }

}