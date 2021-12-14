package com.marcos.movies.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.marcos.domain.Movie


class MovieDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setMovie(movie: Movie) = with(movie) {
        text = buildSpannedString {

            bold { append("Original language: ") }

            append(originalLanguage)

            bold { append("Original title: ") }
            append(originalTitle)

            bold { append("Release date: ") }
            append(releaseDate)

            bold { append("Popularity: ") }
            append(popularity.toString())

            bold { append("Vote Average: ") }
            append(voteAverage.toString())
        }
    }
}