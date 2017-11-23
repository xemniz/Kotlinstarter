package ru.xmn.common.extensions

import android.graphics.*
import android.view.View

fun getBitmapFromView(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val c = Canvas(bitmap)
    view.layout(view.left, view.top, view.right, view.bottom)
    view.draw(c)
    return bitmap
}

fun Bitmap.alphaPadding(resizeFactor: Float): Bitmap {
    val bmOverlay = Bitmap.createBitmap(width, height, config)
    val canvas = Canvas(bmOverlay)
    canvas.drawColor(Color.TRANSPARENT);
    val widthPadding = width - width * resizeFactor
    val heightPadding = height - height * resizeFactor
    canvas.drawBitmap(
            this,
            null,
            RectF(
                    widthPadding / 2,
                    heightPadding / 2,
                    widthPadding / 2,
                    heightPadding / 2),
            null)
    return bmOverlay
}


fun Bitmap.highlightImage(resizeFactor: Float): Bitmap {
    val widthPadding = width - width * resizeFactor
    val heightPadding = height - height * resizeFactor

    // create new bitmap, which will be painted and becomes result image
    val bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    // setup canvas for painting
    val canvas = Canvas(bmOut)
    // setup default color
    canvas.drawColor(0, PorterDuff.Mode.CLEAR)
    // create a blur paint for capturing alpha
    val ptBlur = Paint()
    ptBlur.maskFilter = BlurMaskFilter(35f, BlurMaskFilter.Blur.NORMAL)
    val offsetXY = IntArray(2)
    // capture alpha into a bitmap
    val bmAlpha = extractAlpha(ptBlur, offsetXY)
    // create a color paint
    val ptAlphaColor = Paint()
    ptAlphaColor.color = -0x1
    // paint color for captured alpha region (bitmap)
    canvas.drawBitmap(bmAlpha, offsetXY[0].toFloat() , offsetXY[1].toFloat(), ptAlphaColor)
    // free memory
    bmAlpha.recycle()

    // paint the image source
    val matrix = Matrix().apply {
        postScale(resizeFactor, resizeFactor)
        postTranslate(widthPadding / 2, heightPadding / 2)
    }
    canvas.drawBitmap(this, matrix, null)

    // return out final image
    return bmOut
}
