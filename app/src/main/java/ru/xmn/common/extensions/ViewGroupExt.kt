package ru.xmn.common.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val ViewGroup.views: List<View>
    get() = (0 until getChildCount()).map { getChildAt(it) }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, init: View.() -> Unit = {}): View {
    val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
    view.init()
    return view
}