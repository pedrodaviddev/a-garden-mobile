package com.pedrodavidmcr.agarden.plants.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.items_plants.view.*

class PlantsAdapter : RecyclerView.Adapter<PlantsAdapter.PlantsHolder>() {
  val list = listOf(
      Plant("Prueba", 45.4, 36.4),
      Plant("aaaa", 15.4, 36.4),
      Plant("ewewe", 40.4, 36.4),
      Plant("wewa", 25.4, 36.4),
      Plant("asdxc", 34.4, 36.4),
      Plant("zxc", 43.4, 36.4),
      Plant("zxc", 10.4, 36.4),
      Plant("zxczcx", 45.4, 36.4),
      Plant("zxc", 40.4, 36.4),
      Plant("Prueba", 45.4, 36.4),
      Plant("Prueba", 25.4, 36.4),
      Plant("Prueba", 35.4, 36.4),
      Plant("Prueba", 15.4, 36.4),
      Plant("Prueba", 5.4, 36.4)
  )

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsHolder =
      PlantsHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.items_plants, parent, false))

  override fun onBindViewHolder(holder: PlantsHolder, position: Int) =
      holder.setData(list[position])

  override fun getItemCount(): Int = list.size

  inner class PlantsHolder(private val item: View) : RecyclerView.ViewHolder(item) {
    fun setData(plant: Plant) {
      with(item) {
        plantImage.setCircleBackgroundColorResource(android.R.color.holo_blue_light)
        plantImage.setImageResource(R.drawable.flower)
        plantSun.text = "${plant.sunLight} %"
        plantSunBar.progress = plant.sunLight.toInt()
        plantTemperature.text = "${plant.temperature} C"
        plantTemperatureBar.progress = plant.temperature.toInt()
        plantName.text = plant.name
      }
    }
  }

}