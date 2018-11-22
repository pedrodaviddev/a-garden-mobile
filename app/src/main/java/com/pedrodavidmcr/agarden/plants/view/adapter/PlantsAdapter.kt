package com.pedrodavidmcr.agarden.plants.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.domain.Plant
import kotlinx.android.synthetic.main.items_plants.view.*

class PlantsAdapter(
  var trigger: (TextView, CardView, ImageView, Plant) -> Unit
) : RecyclerView.Adapter<PlantsAdapter.PlantsHolder>() {
  var list = listOf<Plant>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsHolder =
    PlantsHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.items_plants, parent, false)
    )

  override fun onBindViewHolder(holder: PlantsHolder, position: Int) =
    holder.setData(list[position])

  override fun getItemCount(): Int = list.size

  inner class PlantsHolder(private val item: View) : ViewHolder(item) {
    fun setData(plant: Plant) {
      ViewCompat.setTransitionName(item.rootCard, "${plant.name}Card")
      ViewCompat.setTransitionName(item.plantImage, "${plant.name}Image")
      ViewCompat.setTransitionName(item.plantName, "${plant.name}Name")
      item.setOnClickListener {
        trigger(item.plantName, item.rootCard, item.plantImage, plant)
      }
      with(item) {
        plantImage.setCircleBackgroundColorResource(android.R.color.holo_blue_light)
        plantImage.setImageResource(R.drawable.hierbabuena)
        plantTemperature.text = "${plant.temperature.toInt()} C"
        plantTemperatureBar.progress = plant.temperature.toInt()
        plantSun.text = "${plant.sunLight.toInt()} lux"
        plantSunBar.progress = plant.sunLight.toInt()
        plantName.text = plant.name
      }
    }
  }

}