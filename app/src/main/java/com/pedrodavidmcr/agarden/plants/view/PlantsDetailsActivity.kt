package com.pedrodavidmcr.agarden.plants.view

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.LayoutInflater
import android.view.animation.Animation
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.activity_plants_details.*
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.dip
import org.jetbrains.anko.textAppearance

class PlantsDetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plants_details)
    supportPostponeEnterTransition()
    window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
      override fun onTransitionEnd(transition: Transition?) {
        bar.startAnimation(ProgressAnimation(bar, 0F, 90F).apply { duration = 1000 })
        bar2.startAnimation(ProgressAnimation(bar2, 0F, 100F).apply { duration = 500 })
        bar3.startAnimation(ProgressAnimation(bar3, 0F, 70F).apply { duration = 700 })
      }

      override fun onTransitionResume(transition: Transition?) = Unit
      override fun onTransitionPause(transition: Transition?) = Unit
      override fun onTransitionCancel(transition: Transition?) = Unit
      override fun onTransitionStart(transition: Transition?) = Unit

    })
    ViewCompat.setTransitionName(maincard, intent.extras.getString("transitionRoot"))
    ViewCompat.setTransitionName(image, intent.extras.getString("transitionImage"))
    configuration.adapter = SettingsAdapter(supportFragmentManager)
    tabLayout.setupWithViewPager(configuration)
    tabLayout.getTabAt(0)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Hour"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textSize = 11F
            compoundDrawablePadding = dip(5)

            setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0)
          }
        }
    tabLayout.getTabAt(1)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Light"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textSize = 11F
            compoundDrawablePadding = dip(5)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.sun, 0, 0, 0)
          }
        }
    tabLayout.getTabAt(2)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Humidity"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textSize = 11F
            compoundDrawablePadding = dip(5)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.humidity, 0, 0, 0)
          }
        }
    supportStartPostponedEnterTransition()


  }

  override fun onBackPressed() {
    val listener = { super.onBackPressed() }
    bar.startAnimation(ProgressAnimation(bar, bar.progress.toFloat(), 0F).apply {
      duration = 1000
      setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) = listener()
        override fun onAnimationStart(animation: Animation?) = Unit

      })
    })
    bar2.startAnimation(ProgressAnimation(bar2, bar2.progress.toFloat(), 0F).apply { duration = 500 })
    bar3.startAnimation(ProgressAnimation(bar3, bar3.progress.toFloat(), 0F).apply { duration = 700 })
  }
}
