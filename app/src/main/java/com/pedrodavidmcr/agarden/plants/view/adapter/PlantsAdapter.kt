package com.pedrodavidmcr.agarden.plants.view.adapter

import androidx.core.view.ViewCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.domain.Plant
import kotlinx.android.synthetic.main.items_plants.view.*

class PlantsAdapter(var trigger: (TextView, androidx.cardview.widget.CardView, ImageView, Plant) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<PlantsAdapter.PlantsHolder>() {
  var list = listOf<Plant>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsHolder =
      PlantsHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.items_plants, parent, false))

  override fun onBindViewHolder(holder: PlantsHolder, position: Int) =
      holder.setData(list[position])

  override fun getItemCount(): Int = list.size

  inner class PlantsHolder(private val item: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(item) {
    fun setData(plant: Plant) {
      ViewCompat.setTransitionName(item.rootCard, "${plant.name}Card")
      ViewCompat.setTransitionName(item.plantImage, "${plant.name}Image")
      ViewCompat.setTransitionName(item.plantName, "${plant.name}Name")
      item.setOnClickListener {
        trigger(item.plantName, item.rootCard, item.plantImage, plant)
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