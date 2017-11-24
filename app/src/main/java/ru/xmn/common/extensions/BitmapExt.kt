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
    val widthPadding = width - width * resizeFactor
    val heightPadding = height - height * resizeFactor

    // create new bitmap, which will be painted and becomes result image
    val bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    // setup canvas for painting
    val canvas = Canvas(bmOut)
    // setup default color
    val colour = (0 and 0xFF) shl 24;
    canvas.drawColor(colour, PorterDuff.Mode.DST_IN);

    // paint the image source
    val matrix = Matrix().apply {
        postScale(resizeFactor, resizeFactor)
        postTranslate(widthPadding / 2, heightPadding / 2)
    }
    canvas.drawBitmap(this, matrix, null)
    recycle()

    // return out final image
    return bmOut
}
