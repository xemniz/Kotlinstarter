package ru.xmn.common.extensions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


fun ImageView.loadUrl(url: String, loadingView: View? = null, transformations: List<Transformation<Bitmap>> = emptyList()) {
    val load = Glide.with(context)
            .load(url)
    if (transformations.isNotEmpty())
        load.apply(RequestOptions().transform(MultiTransformation(transformations)))
    load.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            loadingView?.visibility = View.GONE
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            loadingView?.visibility = View.GONE
            return false
        }
    })
            .into(this)

}