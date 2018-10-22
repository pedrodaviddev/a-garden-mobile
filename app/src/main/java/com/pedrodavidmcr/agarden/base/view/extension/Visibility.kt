package com.pedrodavidmcr.agarden.base.view.extension

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.setVisible() {
  visibility = VISIBLE
}
fun View.setGone() {
  visibility = GONE
}