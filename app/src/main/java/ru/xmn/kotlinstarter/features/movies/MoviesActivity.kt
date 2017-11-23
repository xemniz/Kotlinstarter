package ru.xmn.kotlinstarter.features.movies

import android.os.Bundle
import android.renderscript.RenderScript
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_movies.*
import org.jetbrains.anko.contentView
import ru.xmn.blur.RSBlurProcessor
import ru.xmn.common.extensions.alphaPadding
import ru.xmn.common.extensions.getBitmapFromView
import ru.xmn.common.extensions.highlightImage
import ru.xmn.common.extensions.onGlobalLayout
import ru.xmn.kotlinstarter.R


class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        contentView!!.onGlobalLayout {
            blurBackForPoster(poster, posterBlurBack)
        }
    }

    private fun blurBackForPoster(poster: ImageView, back: ImageView) {
        val posterBitmap = getBitmapFromView(poster).highlightImage(.8f)
//        val renderScript = RenderScript.create(poster.context)
//        val blurredPoster = RSBlurProcessor(renderScript).blur(posterBitmap, 25f, 4)
        back.apply {
            setImageBitmap(posterBitmap)
            alpha = .4f
        }
    }
}
