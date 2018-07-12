package com.pedrodavidmcr.agarden.plants.data

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson
import com.pedrodavidmcr.agarden.main.URL
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class RemotePlantsRepository : PlantsRepository {
  override fun updatePlant(plant: Plant) {
    val json = Gson().toJson(plant)
    "$URL/plant/${plant.id}".httpPut()
        .body(json).also {
         it.headers["Content-Type"] = "application/json"
        }.responseObject<Any>().third.fold(
        success = {},
        failure = { throw Exception(it.message) }
    )
  }

  override fun getAllPlants(): List<Plant> =
      "$URL/plants".httpGet()
          .responseObject<List<Plant>>().third
          .fold(success = { return it },
              failure = { throw Exception(it.message) })
}
