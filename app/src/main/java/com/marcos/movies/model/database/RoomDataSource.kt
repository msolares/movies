package com.marcos.movies.model.database

import com.antonioleiva.mymovies.model.database.Movie as RoomMovie
import com.antonioleiva.mymovies.model.database.MovieDatabase
import com.marcos.data.source.LocalDataSource
import com.marcos.movies.model.toDomainMovie
import com.marcos.movies.model.toRoomMovie
import com.marcos.domain.Movie as DomainMovie

class RoomDataSource(private val db: MovieDatabase):
    LocalDataSource {

    private val movieDao = db.movieDao()

    override fun isEmpty(): Boolean = movieDao.movieCount() <= 0

    override fun saveMovies(movies: List<DomainMovie>) {
        movieDao.insertMovies(movies.map{it.toRoomMovie()})
    }

    override fun getPopularMovies(): List<DomainMovie> = movieDao.getAll().map { it.toDomainMovie() }
}


