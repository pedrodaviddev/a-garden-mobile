package com.pedrodavidmcr.agarden.plants.data

import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.pedrodavidmcr.agarden.main.URL
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository

class RemoteSamplesRepository : SamplesRepository {
  override fun getSamplesFrom(plantId: Int): List<Sample> {
    "$URL/sample/$plantId".httpGet()
        .responseObject<List<Sample>>().third
        .fold(success = { return it },
            failure = { throw Exception() })
  }
}