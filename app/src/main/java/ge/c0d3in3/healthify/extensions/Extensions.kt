package ge.c0d3in3.healthify.extensions

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import java.util.*

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Double.formatToWeight() = String.format("%.1f", this)

fun SpannableString.span(
    color: Int,
    start: Int,
    end: Int,
    flag: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
) {
    this.setSpan(ForegroundColorSpan(color), start, end, flag)
}

fun getToday(): Long {
    val c = Calendar.getInstance()
    c.timeInMillis = System.currentTimeMillis()
    c[Calendar.HOUR_OF_DAY] = 0
    c[Calendar.MINUTE] = 0
    c[Calendar.SECOND] = 0
    c[Calendar.MILLISECOND] = 0
    return c.timeInMillis
}

fun getTomorrow(): Long {
    val c: Calendar = Calendar.getInstance()
    c.timeInMillis = System.currentTimeMillis()
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 1)
    c.set(Calendar.MILLISECOND, 0)
    c.add(Calendar.DATE, 1)
    return c.timeInMillis
}

fun View.visibleIf(visible: Boolean) {
    if(visible) this.show()
    else this.gone()
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