package com.pedrodavidmcr.agarden.plants.view

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.activity_plants_details.*
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding
import org.jetbrains.anko.textAppearance

class PlantsDetailsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plants_details)
    supportPostponeEnterTransition()
    ViewCompat.setTransitionName(maincard, intent.extras.getString("transitionName"))
    configuration.adapter = SettingsAdapter(supportFragmentManager)
    tabLayout.setupWithViewPager(configuration)
    tabLayout.getTabAt(0)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Hour"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            compoundDrawablePadding = dip(8)

            setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0)
          }
        }
    tabLayout.getTabAt(1)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Light"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            compoundDrawablePadding = dip(8)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.sun, 0, 0, 0)
          }
        }
    tabLayout.getTabAt(2)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Humidity"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            compoundDrawablePadding = dip(8)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.humidity, 0, 0, 0)
          }
        }
    supportStartPostponedEnterTransition()

  }
}
