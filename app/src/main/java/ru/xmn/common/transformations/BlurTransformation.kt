package ru.xmn.common.transformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.renderscript.RSRuntimeException
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource
import jp.wasabeef.glide.transformations.internal.FastBlur
import jp.wasabeef.glide.transformations.internal.RSBlur
import java.security.MessageDigest

class BlurTransformation @JvmOverloads
constructor(context: Context,
            private val mBitmapPool: BitmapPool = Glide.get(context).bitmapPool,
            private val mRadius: Int = MAX_RADIUS, private val mSampling: Int = DEFAULT_DOWN_SAMPLING)
    : Transformation<Bitmap> {

    private val ID = "com.bumptech.glide.load.resource.bitmap.BlurTransformation"

    private val ID_BYTES = ID.toByteArray(Key.CHARSET)


    private val mContext: Context
    constructor(context: Context, radius: Int) : this(context, Glide.get(context).bitmapPool, radius, DEFAULT_DOWN_SAMPLING) {}

    constructor(context: Context, radius: Int, sampling: Int) : this(context, Glide.get(context).bitmapPool, radius, sampling) {}

    init {
        mContext = context.applicationContext
    }

    val id: String
        get() = "BlurTransformation(radius=$mRadius, sampling=$mSampling)"

    override fun transform(context: Context?, resource: Resource<Bitmap>, outWidth: Int, outHeight: Int): Resource<Bitmap> {
        val source = resource.get()

        val width = source.width
        val height = source.height
        val scaledWidth = width / mSampling
        val scaledHeight = height / mSampling

        var bitmap: Bitmap? = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap!!)
        canvas.scale(1 / mSampling.toFloat(), 1 / mSampling.toFloat())
        val paint = Paint()
        paint.flags = Paint.FILTER_BITMAP_FLAG
        canvas.drawBitmap(source, 0f, 0f, paint)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(mContext, bitmap, mRadius)
            } catch (e: RSRuntimeException) {
                bitmap = FastBlur.blur(bitmap, mRadius, true)
            }

        } else {
            bitmap = FastBlur.blur(bitmap, mRadius, true)
        }

        return BitmapResource.obtain(bitmap, mBitmapPool)!!
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {

        private val MAX_RADIUS = 25
        private val DEFAULT_DOWN_SAMPLING = 1
    }
}