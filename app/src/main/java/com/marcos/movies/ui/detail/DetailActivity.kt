package com.marcos.movies.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcos.movies.R
import com.marcos.movies.databinding.ActivityDetailBinding
import com.marcos.movies.models.Movie
import com.marcos.movies.ui.common.Scope
import com.marcos.movies.ui.common.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailPresenter.View {
    companion object {
        const val MOVIE = "DetailActivity:movie"
    }

    private val presenter = DetailPresenter()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityDetailBinding.inflate(layoutInflater).run {
            setContentView(root)
        }
        val movie: Movie? = intent.getParcelableExtra(MOVIE)
        if (movie != null) {
            presenter.onCreate(this, movie)
        }
    }

    override fun updateUi(movie: Movie) = with(movie){
        movieDetailToolbar.title = title
        movieDetailImage.loadUrl("https://image.tmdb.org/t/p/w780$backdropPath")
        movieDetailSummary.text = overview
        movieDetailInfo.setMovie(movie)
    }
}