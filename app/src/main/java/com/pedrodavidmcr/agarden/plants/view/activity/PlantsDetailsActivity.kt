package com.pedrodavidmcr.agarden.plants.view.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.view.DataView
import com.pedrodavidmcr.agarden.base.view.animation.setOnlyEndAnimation
import com.pedrodavidmcr.agarden.base.view.color
import com.pedrodavidmcr.agarden.irrigation.view.activity.PlantConfigurationActivity
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.presenter.PlantDetailPresenter
import com.pedrodavidmcr.agarden.plants.view.animation.ProgressAnimation
import com.pedrodavidmcr.agarden.plants.view.getSharedPlant
import kotlinx.android.synthetic.main.activity_plants_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class PlantsDetailsActivity : AppCompatActivity(), DataView<Sample> {
  private val presenter: PlantDetailPresenter by lazy { PlantDetailPresenter(this) }
  private val plant: Plant by lazy { intent.getSharedPlant() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plants_details)
    animateActivityEntry()
    image.onClick {
      startActivity<PlantConfigurationActivity>()
    }
    formatCharts(temperatureStat, humidityStat)
    presenter.getSamplesFrom(plant)
  }

  private fun formatCharts(vararg charts: LineChart) {
    charts.forEach {
      it.setDrawBorders(false)
      it.setBorderWidth(0F)
      it.axisRight.setDrawLabels(false)
      it.xAxis.setDrawLabels(false)
      it.setBackgroundColor(color(android.R.color.white))
      it.legend.setCustom(emptyArray())
    }
  }

  override fun onDataLoaded(data: List<Sample>) {
    val temperatures = data
        .mapIndexed { index, sample -> Entry(index.toFloat(), sample.temperature.toFloat()) }
    val humidity = data
        .mapIndexed { index, sample -> Entry(index.toFloat(), sample.humidity.toFloat()) }

    val tempDataSet = LineDataSet(temperatures, "temperatures").apply {
      lineWidth = 6F
      color = color(android.R.color.holo_green_light)
    }
    val humDataSet = LineDataSet(humidity, "humidity").apply {
      lineWidth = 6F
      color = color(android.R.color.holo_blue_light)
    }

    configureTemperatureChart(tempDataSet)
    configureHumidityChart(humDataSet)
  }

  private fun configureTemperatureChart(tempDataSet: LineDataSet) = with(temperatureStat) {
    data = LineData(tempDataSet)
    description = Description().also { it.text = "Last temperature measures" }
    setVisibleYRange(0F, 50F, YAxis.AxisDependency.LEFT)
    fitScreen()
    invalidate()
  }

  private fun configureHumidityChart(humDataSet: LineDataSet) = with(humidityStat) {
    data = LineData(humDataSet)
    description = Description().also { it.text = "Last humidity measures" }
    setVisibleYRange(0F, 100F, YAxis.AxisDependency.LEFT)
    fitScreen()
    invalidate()
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
