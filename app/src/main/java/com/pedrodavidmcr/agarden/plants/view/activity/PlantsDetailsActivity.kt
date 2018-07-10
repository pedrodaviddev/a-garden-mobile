package com.pedrodavidmcr.agarden.plants.view.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.view.animation.setOnlyEndAnimation
import com.pedrodavidmcr.agarden.irrigation.view.activity.PlantConfigurationActivity
import com.pedrodavidmcr.agarden.plants.view.animation.ProgressAnimation
import kotlinx.android.synthetic.main.activity_plants_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class PlantsDetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plants_details)
    animateActivityEntry()

    image.onClick {
      startActivity<PlantConfigurationActivity>()
    }
  }

  private fun animateActivityEntry() {
    supportPostponeEnterTransition()
    window.sharedElementEnterTransition.setOnlyEndAnimation {
      bar.startAnimation(ProgressAnimation(bar, 0F, 90F).apply { duration = 1000 })
      bar2.startAnimation(ProgressAnimation(bar2, 0F, 100F).apply { duration = 500 })
      bar3.startAnimation(ProgressAnimation(bar3, 0F, 70F).apply { duration = 700 })
    }
    ViewCompat.setTransitionName(maincard, intent.extras.getString("transitionRoot"))
    ViewCompat.setTransitionName(image, intent.extras.getString("transitionImage"))
    supportStartPostponedEnterTransition()
  }

  override fun onBackPressed() {
    bar.startAnimation(ProgressAnimation(bar, bar.progress.toFloat(), 0F).apply {
      duration = 1000
      setOnlyEndAnimation { super.onBackPressed() }
    })
    bar2.startAnimation(ProgressAnimation(bar2, bar2.progress.toFloat(), 0F).apply { duration = 500 })
    bar3.startAnimation(ProgressAnimation(bar3, bar3.progress.toFloat(), 0F).apply { duration = 700 })
  }
}
