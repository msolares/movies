package com.marcos.usecases

import com.marcos.data.repository.MoviesRepository
import com.marcos.testshared.mockedMovie
import com.marcos.usescases.FindMovieById
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FindMovieByIdTest {
    lateinit var moviesRepository: MoviesRepository
    lateinit var findMovieById: FindMovieById

    @Before
    fun setUp() {
        findMovieById = FindMovieById(moviesRepository)
    }

    @Test
    fun `invoke calls movies repository`() {
        runBlocking {
            val movie = mockedMovie.copy(id = 1)
            whenever(moviesRepository.findById(1)).thenReturn(movie)
            val result = findMovieById.invoke(1)
            assertEquals(movie, result)
        }
    }

}