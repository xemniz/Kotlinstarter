package ru.xmn.kotlinstarter.features.movies

import android.renderscript.RenderScript
import android.widget.ImageView
import ru.xmn.blur.RSBlurProcessor
import ru.xmn.common.extensions.alphaPadding
import ru.xmn.common.extensions.getBitmapFromView
import ru.xmn.common.extensions.onGlobalLayout

fun blurBackForPoster(poster: ImageView, back: ImageView, resizeFactor: Float) {
    poster.onGlobalLayout {
        val posterBitmap = getBitmapFromView(poster).alphaPadding(resizeFactor)
        val renderScript = RenderScript.create(poster.context)
        val blurredPoster = RSBlurProcessor(renderScript).blur(posterBitmap, 120f, 3)
        back.apply {
            setImageBitmap(blurredPoster)
            alpha = .16f
        }
    }
}