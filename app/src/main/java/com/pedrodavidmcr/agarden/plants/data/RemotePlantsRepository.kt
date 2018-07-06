package com.pedrodavidmcr.agarden.plants.data

import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.pedrodavidmcr.agarden.main.URL
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class RemotePlantsRepository : PlantsRepository {
  override fun getAllPlants(): List<Plant> =
      "${URL}/plants".httpGet()
          .responseObject<List<Plant>>().third
          .fold(success = { return it },
              failure = { throw Exception() })

}