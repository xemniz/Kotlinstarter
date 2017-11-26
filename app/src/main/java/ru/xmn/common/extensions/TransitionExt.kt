package ru.xmn.common.extensions

import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.View

fun Transition.delay(i: Long): Transition {
    this.startDelay = i; return this
}

fun Transition.dur(i: Long): Transition {
    this.duration = i; return this
}

fun View.pairSharedTransition(): android.support.v4.util.Pair<View, String> {
    return android.support.v4.util.Pair<View, String>(this, ViewCompat.getTransitionName(this))
}

inline fun <reified T> AppCompatActivity.startActivityWithTransition(vararg views: View) {
    val intent = Intent(this, T::class.java)
    val args = views.map { it.pairSharedTransition() }.toTypedArray()
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *args)
    ContextCompat.startActivity(this, intent, options.toBundle())
}