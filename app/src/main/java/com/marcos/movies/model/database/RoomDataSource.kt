package com.marcos.movies.model.database

import com.marcos.data.source.LocalDataSource
import com.marcos.movies.model.toDomainMovie
import com.marcos.movies.model.toRoomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.marcos.domain.Movie as DomainMovie

class RoomDataSource(private val db: MovieDatabase):
    LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) {
            movieDao.movieCount() <= 0
        }

    override suspend fun saveMovies(movies: List<DomainMovie>) {
        withContext(Dispatchers.IO) {
            movieDao.insertMovies(movies.map { it.toRoomMovie() })
        }
    }

    override suspend fun getPopularMovies(): List<DomainMovie> =
        withContext(Dispatchers.IO) {
            movieDao.getAll().map { it.toDomainMovie() }
        }


    override suspend fun findById(id: Int): com.marcos.domain.Movie = withContext(Dispatchers.IO) {
        movieDao.findById(id).toDomainMovie()
    }

    override suspend fun findByName(name: String): List<DomainMovie> =
        withContext(Dispatchers.IO){
            movieDao.getSearch("%$name%").map {
                it.toDomainMovie()
            }
        }

    override suspend fun update(movie: DomainMovie) {
        withContext(Dispatchers.IO) { movieDao.updateMovie(movie.toRoomMovie()) }
    }
}


