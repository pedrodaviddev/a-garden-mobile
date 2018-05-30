package com.pedrodavidmcr.agarden.plants.view

import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.domain.Plant
import kotlinx.android.synthetic.main.items_plants.view.*

class PlantsAdapter(var trigger: (CardView, ImageView, Plant) -> Unit) : RecyclerView.Adapter<PlantsAdapter.PlantsHolder>() {
  var list = listOf<Plant>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsHolder =
      PlantsHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.items_plants, parent, false))

  override fun onBindViewHolder(holder: PlantsHolder, position: Int) =
      holder.setData(list[position])

  override fun getItemCount(): Int = list.size

  inner class PlantsHolder(private val item: View) : RecyclerView.ViewHolder(item) {
    fun setData(plant: Plant) {
      ViewCompat.setTransitionName(item.rootCard, "${plant.name}Card")
      ViewCompat.setTransitionName(item.plantImage, "${plant.name}Image")
      item.setOnClickListener {
        trigger(item.rootCard, item.plantImage, plant)
      }
      with(item) {
        plantImage.setCircleBackgroundColorResource(android.R.color.holo_blue_light)
        plantImage.setImageResource(R.drawable.flower)
        plantTemperature.text = "${plant.temperature.toInt()} C"
        plantTemperatureBar.progress = plant.temperature.toInt()
        plantSun.text = "${plant.sunLight.toInt()} %"
        plantSunBar.progress = plant.sunLight.toInt()
        plantName.text = plant.name
      }
    }
  }

}