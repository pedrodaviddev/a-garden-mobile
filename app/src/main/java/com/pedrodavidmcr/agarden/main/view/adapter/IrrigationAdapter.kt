package com.pedrodavidmcr.agarden.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.main.model.Irrigation
import com.pedrodavidmcr.agarden.plants.domain.Plant
import khronos.toString
import kotlinx.android.synthetic.main.items_irrigation.view.*
import java.util.*

class IrrigationAdapter(var plantList: List<Plant>) :
  RecyclerView.Adapter<IrrigationAdapter.IrrigationHolder>() {
  var data: List<Pair<Irrigation,Plant>> = emptyList()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IrrigationHolder =
    IrrigationHolder(
      LayoutInflater.from(parent.context).inflate(
        R.layout.items_irrigation,
        parent,
        false
      )
    )

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: IrrigationHolder, position: Int) =
    holder.setData(data[position])


  inner class IrrigationHolder(private val item: View) : ViewHolder(item) {
    fun setData(irrigation: Pair<Irrigation, Plant>) {
      item.irrigationText.text = "Se ha efectuado el riego de la maceta con" +
          " ${irrigation.second.name} a las ${
          Calendar.getInstance().also {
            it.timeInMillis = irrigation.first.date
          }.time.toString("HH:mm dd/MM")
          }"

    }
  }
}