package com.pedrodavidmcr.agarden.base.view

import android.content.Context
import android.support.v4.content.ContextCompat

fun Context.color(id: Int) = ContextCompat.getColor(this, id)