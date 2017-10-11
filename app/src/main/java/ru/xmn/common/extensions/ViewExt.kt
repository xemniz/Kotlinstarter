package ru.xmn.common.extensions

import android.content.res.Resources
import android.graphics.Rect
import android.support.v4.view.ViewCompat
import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}
fun View.gone() {
    this.visibility = View.GONE
}