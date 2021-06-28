package ge.c0d3in3.components

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Double.formatToWeight() = String.format("%.1f", this)

