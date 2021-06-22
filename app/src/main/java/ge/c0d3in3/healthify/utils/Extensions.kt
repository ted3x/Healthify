package ge.c0d3in3.healthify.utils

import android.view.View

fun View.onClick(delay: Long = 500, callback: () -> Unit) {
    this.setOnClickListener {
        callback.invoke()
        this.isEnabled = false
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}