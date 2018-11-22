package com.pedrodavidmcr.agarden.irrigation.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.Injector
import com.pedrodavidmcr.agarden.base.view.extension.color
import com.pedrodavidmcr.agarden.plants.Humidity
import com.pedrodavidmcr.agarden.plants.domain.Configuration
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.view.adapter.SettingsAdapter
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantConfigurationViewModel
import kotlinx.android.synthetic.main.activity_plant_configuration.*
import org.jetbrains.anko.childrenSequence
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textAppearance
import org.jetbrains.anko.textColor

class PlantConfigurationActivity : AppCompatActivity() {
  private lateinit var viewModel: PlantConfigurationViewModel
  val plantId: Int by lazy { intent.getIntExtra("plantId", -1) }
  private val adapter: SettingsAdapter by lazy { SettingsAdapter(supportFragmentManager) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plant_configuration)
    title = "Configuración de riego"

    val factory = Injector.providePlantConfigurationViewModelFactory(plantId)
    viewModel = ViewModelProviders.of(this, factory)
        .get(PlantConfigurationViewModel::class.java)

    setUpIrrigationParameters()
    setCurrentConfiguration()
    initButton()
  }

  private fun initButton() {
    updateButton.onClick {
      selectNewPlantConfiguration()
      viewModel.updatePlantConfiguration()
      finish()
    }
  }

  private fun selectNewPlantConfiguration() {
    viewModel.plant.value = viewModel.plant.value!!.copy(
        requiredHumidity = getSelectedHumidityRequirements(),
        configuration = adapter.getConfiguration(configuration.currentItem)
    )
  }


  private fun setCurrentConfiguration() {
    viewModel.plant.observe(this, Observer { plant ->
      setCurrentHumidityRequirements(plant)
      setCurrentIrrigationMode(plant)
    })
  }

  private fun setCurrentIrrigationMode(plant: Plant) {
    configuration.currentItem = if (plant.configuration == Configuration.BY_HUMIDITY) 1 else {
      adapter.setConfiguration(plant.configuration)
      0
    }
  }

  private fun setCurrentHumidityRequirements(plant: Plant) = plant.run {
    lowHumidity.isChecked = requiredHumidity == Humidity.LOW
    mediumHumidity.isChecked = requiredHumidity == Humidity.MEDIUM
    highHumidity.isChecked = requiredHumidity == Humidity.HIGH
    maxHumidity.isChecked = requiredHumidity == Humidity.VERY_HIGH
  }

  private fun getSelectedHumidityRequirements() = when {
    lowHumidity.isChecked -> Humidity.LOW
    mediumHumidity.isChecked -> Humidity.MEDIUM
    highHumidity.isChecked -> Humidity.HIGH
    else -> Humidity.VERY_HIGH
  }

  private fun setUpIrrigationParameters() {
    configuration.adapter = adapter
    tabLayout.setupWithViewPager(configuration)
    tabLayout.getTabAt(0)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = context.getString(R.string.scheduled)
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textSize = 11F
            compoundDrawablePadding = dip(5)
            textColor = color(android.R.color.white)
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.clock, 0, 0, 0)
          }
        }
    tabLayout.getTabAt(1)!!.customView = LayoutInflater.from(this)
        .inflate(R.layout.tab_settings, null).childrenSequence().filter { it is TextView }
        .first().apply {
          (this as TextView).apply {
            text = context.getString(R.string.mantain_humidity)
            textAppearance = android.R.style.TextAppearance_Material_Widget_TabWidget
            textColor = color(android.R.color.white)
            textSize = 11F
            compoundDrawablePadding = dip(5)
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.humidity, 0, 0, 0)
          }
        }
  }
}
