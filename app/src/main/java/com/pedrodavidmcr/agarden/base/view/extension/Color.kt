package com.pedrodavidmcr.agarden.base.view.extension

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(id: Int) = ContextCompat.getColor(this, id)