package com.pedrodavidmcr.agarden.base.view.animation

import android.transition.Transition
import android.view.animation.Animation

fun Transition.setOnlyEndAnimation(listener: () -> Unit) {
  this.addListener(object : Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition?) = listener()
    override fun onTransitionResume(transition: Transition?) = Unit
    override fun onTransitionPause(transition: Transition?) = Unit
    override fun onTransitionCancel(transition: Transition?) = Unit
    override fun onTransitionStart(transition: Transition?) = Unit
  })
}

fun Animation.setOnlyEndAnimation(listener: () -> Unit) {
  this.setAnimationListener(object : Animation.AnimationListener {
    override fun onAnimationEnd(animation: Animation?) = listener()
    override fun onAnimationRepeat(animation: Animation?) = Unit
    override fun onAnimationStart(animation: Animation?) = Unit
  })
}