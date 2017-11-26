package ru.xmn.kotlinstarter.features.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movies.*
import ru.xmn.common.extensions.startActivityWithTransition
import ru.xmn.kotlinstarter.R


class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        blurBackForPoster(poster, posterBlurBack, .75f)

        poster.setOnClickListener {
            startActivityWithTransition<MovieDetailsActivity>(posterCard, emptyForTransition, playTrailerButton)
        }
    }
}
