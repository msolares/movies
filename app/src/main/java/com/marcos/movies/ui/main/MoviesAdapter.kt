package com.marcos.movies.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcos.domain.Movie
import com.marcos.movies.R
import com.marcos.movies.databinding.ItemMovieBinding
import com.marcos.movies.ui.common.basicDiffUtil
import com.marcos.movies.ui.common.inflate
import com.marcos.movies.ui.common.loadUrl

class MoviesAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_movie
            , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieBinding.bind(view)
        fun bind(movie: Movie) = with(binding) {
            tvMovieName.text = movie.title
            ivMovie.loadUrl("https://image.tmdb.org/t/p/w185/${movie.posterPath}")
        }
    }
}