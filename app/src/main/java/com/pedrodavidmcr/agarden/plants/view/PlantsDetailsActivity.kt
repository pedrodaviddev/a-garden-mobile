package com.pedrodavidmcr.agarden.plants.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.activity_plants_details.*

class PlantsDetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plants_details)
    supportPostponeEnterTransition()
    val string = intent
    ViewCompat.setTransitionName(maincard,intent.extras.getString("transitionName"))
    supportStartPostponedEnterTransition()
  }
}
