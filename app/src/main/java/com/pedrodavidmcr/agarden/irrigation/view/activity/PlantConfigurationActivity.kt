package com.pedrodavidmcr.agarden.irrigation.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.Humidity
import com.pedrodavidmcr.agarden.plants.domain.Configuration
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.presenter.PlantConfigurationPresenter
import com.pedrodavidmcr.agarden.plants.view.adapter.SettingsAdapter
import com.pedrodavidmcr.agarden.plants.view.getSharedPlant
import kotlinx.android.synthetic.main.activity_plant_configuration.*
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textAppearance

class PlantConfigurationActivity : AppCompatActivity() {
  val plant: Plant by lazy { intent.getSharedPlant() }
  private val adapter: SettingsAdapter by lazy { SettingsAdapter(supportFragmentManager) }
  private val presenter: PlantConfigurationPresenter by lazy { PlantConfigurationPresenter() }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plant_configuration)

    setUpIrrigationParameters()
    setCurrentConfiguration()
    updateButton.onClick {
      presenter.updateConfigurationOf(getUpdatedPlantConfiguration())
      finish()
    }
  }

  private fun getUpdatedPlantConfiguration(): Plant = plant.copy(
      requiredHumidity = when {
        lowHumidity.isChecked -> Humidity.LOW
        mediumHumidity.isChecked -> Humidity.MEDIUM
        highHumidity.isChecked -> Humidity.HIGH
        else -> Humidity.VERY_HIGH
      },
      configuration = adapter.getConfiguration(configuration.currentItem)
  )

  private fun setCurrentConfiguration() {
    setCurrentHumidityRequirements()
    configuration.currentItem = if(plant.configuration == Configuration.BY_HUMIDITY) 1 else {
      adapter.setConfiguration(plant.configuration)
      0
    }
  }

  private fun setCurrentHumidityRequirements() {
    lowHumidity.isChecked = plant.requiredHumidity == Humidity.LOW
    mediumHumidity.isChecked = plant.requiredHumidity == Humidity.MEDIUM
    highHumidity.isChecked = plant.requiredHumidity == Humidity.HIGH
    maxHumidity.isChecked = plant.requiredHumidity == Humidity.VERY_HIGH
  }

  private fun setUpIrrigationParameters() {
    configuration.adapter = adapter
    tabLayout.setupWithViewPager(configuration)
    tabLayout.getTabAt(0)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = "Scheduled"
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
