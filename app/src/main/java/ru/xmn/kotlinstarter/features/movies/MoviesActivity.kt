package ru.xmn.kotlinstarter.features.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeApiServiceUtil
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeStandalonePlayer
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

        playTrailerButton.setOnClickListener {
            startActivity(YouTubeStandalonePlayer.createVideoIntent(this ,
                    YOUTUBE_KEY, "rs7lyEWy36k"));
        }
    }

    private fun setupYoutube() {
        val result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);

        if (result != YouTubeInitializationResult.SUCCESS) {
            //If there are any issues we can show an error dialog.
            result.getErrorDialog(this, 0).show();
        }
    }
}
