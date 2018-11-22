package com.pedrodavidmcr.agarden.main

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.pedrodavidmcr.agarden.main.model.Irrigation
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository

class RemoteIrrigationRepository : IrrigationRepository {
  override fun getIrrigationNumberOfToday(): Int {
    "$URL/todayirrigation".httpGet()
      .responseObject<Int>().third
      .fold(success = { return it },
        failure = {
          Log.d("error", it.message)
          Log.d("error", it.localizedMessage)
          throw Exception(it.message)
        })
  }

  override fun getLastIrrigationList(): List<Irrigation> {
    "$URL/irrigation".httpGet()
      .responseObject<List<Irrigation>>().third
      .fold(success = { return it },
        failure = {
          Log.d("error", it.message)
          Log.d("error", it.localizedMessage)
          throw Exception(it.message)
        })
  }
}