package com.pedrodavidmcr.agarden.plants.view

import android.content.Intent
import android.os.Bundle
import com.pedrodavidmcr.agarden.plants.domain.Plant

fun Plant.toBundle(): Bundle = Bundle().apply {
  putInt("id", id)
  putString("name", name)
  putDouble("temperature", temperature)
  putInt("configuration", configuration)
  putInt("requiredHumidity", requiredHumidity)
  putDouble("sunLight", sunLight)
}

fun Intent.getSharedPlant(): Plant = Plant(
    extras.getInt("id"),
    extras.getString("name"),
    extras.getInt("requiredHumidity"),
    extras.getInt("configuration"),
    extras.getDouble("temperature"),
    extras.getDouble("sunLight")
)