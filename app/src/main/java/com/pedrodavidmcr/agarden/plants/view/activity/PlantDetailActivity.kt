package com.pedrodavidmcr.agarden.plants.view.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.Injector
import com.pedrodavidmcr.agarden.base.view.animation.onFinish
import com.pedrodavidmcr.agarden.base.view.extension.color
import com.pedrodavidmcr.agarden.base.view.extension.setVisible
import com.pedrodavidmcr.agarden.irrigation.view.activity.PlantConfigurationActivity
import com.pedrodavidmcr.agarden.plants.view.animation.ProgressAnimation
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantDetailViewModel
import kotlinx.android.synthetic.main.activity_plant_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity


class PlantDetailActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
  private lateinit var viewModel: PlantDetailViewModel
  private val plantId: Int by lazy { intent.getIntExtra("plantId", -1) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_plant_detail)

    val factory = Injector.providePlantDetailViewModelFactory(plantId)
    viewModel = ViewModelProviders.of(this, factory).get(PlantDetailViewModel::class.java)

    animateActivityEntry()
    restoreStateOfViewsIfActivityRecreation(savedInstanceState)
    initRefreshLayout()
    initCharts()
    image.onClick {
      startActivity<PlantConfigurationActivity>( "plantId" to plantId)
    }
  }

  private fun initRefreshLayout() {
    refreshLayout.setOnRefreshListener(this)
    viewModel.loading.observe(this, Observer {
      refreshLayout.isRefreshing = it
    })
  }

  override fun onRefresh() = viewModel.load()


  private fun restoreStateOfViewsIfActivityRecreation(savedInstanceState: Bundle?) =
      savedInstanceState?.let {
        image.layoutParams.height = image.layoutParams.height / 2
        image.layoutParams.width = image.layoutParams.width / 2
        lightBar.setVisible()
        humidityBar.setVisible()
        temperatureBar.setVisible()
      }


  private fun initCharts() {
    setSharedFormat(temperatureStat, humidityStat)

    viewModel.samples.observe(this, Observer { sampleList ->
      populateTemperatureChart(sampleList.map { it.temperature })
      populateHumidityChart(sampleList.map { it.humidity })
    })
  }

  private fun setSharedFormat(vararg charts: LineChart) = charts.forEach { chart ->
    chart.setBackgroundColor(color(android.R.color.white))
    chart.legend.setCustom(emptyArray())
    chart.axisLeft.axisMinimum = 0F
    chart.xAxis.setDrawGridLines(false)
    chart.axisLeft.setDrawGridLines(false)
    chart.axisRight.setDrawGridLines(false)
    chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    chart.description = Description().also { it.text = "" }
  }

  private fun populateHumidityChart(humidityList: List<Double>) {
    val humidityEntries = humidityList.mapIndexed { index, humidityValue ->
      Entry(index.toFloat(), humidityValue.toFloat())
    }
    val humidityDataSet = LineDataSet(humidityEntries, "Humedad").apply {
      lineWidth = 6F
      color = color(android.R.color.holo_blue_light)
    }
    humidityStat.axisLeft.axisMaximum = 100F
    humidityStat.data = LineData(humidityDataSet)

  }

  private fun populateTemperatureChart(temperatureList: List<Double>) {
    val temperatureEntries = temperatureList.mapIndexed { index, humidityValue ->
      Entry(index.toFloat(), humidityValue.toFloat())
    }
    val temperatureDataSet = LineDataSet(temperatureEntries, "Temperatura").apply {
      lineWidth = 6F
      color = color(android.R.color.holo_green_light)
    }

    temperatureStat.axisLeft.axisMaximum = 45F
    temperatureStat.data = LineData(temperatureDataSet)
  }

  private fun animateActivityEntry() {
    supportPostponeEnterTransition()
    window.sharedElementEnterTransition.onFinish {
      val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 0.5F)
      val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 0.5F)
      val scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(image, pvhX, pvhY)
      val setAnimation = AnimatorSet()
      setAnimation.play(scaleAnimation)
      lightBar.visibility = VISIBLE
      humidityBar.visibility = VISIBLE
      temperatureBar.visibility = VISIBLE
      setAnimation.apply {
        onFinish {
          viewModel.plant.observe(this@PlantDetailActivity, Observer {
            lightText.text = "${it.sunLight} lux"
            temperatureText.text = "${it.temperature} Cº"
            humidityText.text = "${it.humidity} %"
            lightBar
                .startAnimation(ProgressAnimation(lightBar, 0F, (it.sunLight / 10).toFloat())
                    .apply { duration = 1000 })
            humidityBar
                .startAnimation(ProgressAnimation(humidityBar, 0F, it.humidity.toFloat())
                    .apply { duration = 500 })
            temperatureBar
                .startAnimation(ProgressAnimation(temperatureBar,
                    0F,
                    (it.temperature * 2).toFloat())
                    .apply { duration = 700 })

          })
        }
        start()
      }
    }
    viewModel.plant.observe(this, Observer { plant ->
      plantName.text = plant.name
    })

    ViewCompat.setTransitionName(maincard, intent.extras.getString("transitionRoot"))
    ViewCompat.setTransitionName(image, intent.extras.getString("transitionImage"))
    ViewCompat.setTransitionName(plantName, intent.extras.getString("transitionName"))
    supportStartPostponedEnterTransition()
  }

  override fun onBackPressed() {

    lightBar.startAnimation(ProgressAnimation(lightBar, lightBar.progress.toFloat(), 0F).apply {
      duration = 1000
      onFinish {
        val pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5F, 1F)
        val pvhY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5F, 1F)
        val scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(image, pvhX, pvhY)
        val setAnimation = AnimatorSet()
        setAnimation.apply {
          play(scaleAnimation)
          onFinish {
            lightBar.visibility = INVISIBLE
            humidityBar.visibility = INVISIBLE
            temperatureBar.visibility = INVISIBLE
            Handler().postDelayed({
              super.onBackPressed()
            }, 300)
          }
          start()
        }

      }
    })
    humidityBar.startAnimation(ProgressAnimation(humidityBar, humidityBar.progress.toFloat(), 0F).apply { duration = 500 })
    temperatureBar.startAnimation(ProgressAnimation(temperatureBar, temperatureBar.progress.toFloat(), 0F).apply { duration = 700 })
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    outState?.run {
      putBoolean("animationFinished", true)
    }
    super.onSaveInstanceState(outState)
  }
}
