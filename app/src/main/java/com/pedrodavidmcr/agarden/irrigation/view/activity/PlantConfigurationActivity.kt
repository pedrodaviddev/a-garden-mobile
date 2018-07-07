package com.pedrodavidmcr.agarden.irrigation.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.view.adapter.SettingsAdapter
import kotlinx.android.synthetic.main.activity_plant_configuration.*
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.dip
import org.jetbrains.anko.textAppearance

class PlantConfigurationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plant_configuration)

    setUpIrrigationParameters()
  }

  private fun setUpIrrigationParameters() {
    configuration.adapter = SettingsAdapter(supportFragmentManager)
    tabLayout.setupWithViewPager(configuration)
    tabLayout.getTabAt(0)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "At some hour"
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
            text = "Mantain humidity"
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textSize = 11F
            compoundDrawablePadding = dip(5)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.humidity, 0, 0, 0)
          }
        }
  }
}
