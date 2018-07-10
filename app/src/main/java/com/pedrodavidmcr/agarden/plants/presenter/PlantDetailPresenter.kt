package com.pedrodavidmcr.agarden.plants.presenter

import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.base.view.DataView
import com.pedrodavidmcr.agarden.plants.data.RemoteSamplesRepository
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetSamplesFromPlant
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.DataOutput

class PlantDetailPresenter(private val view: DataView<Sample>) : DataOutput<Sample> {
  override fun onLoadData(data: List<Sample>) {
    view.onDataLoaded(data)
  }

  override fun onNoData() {}

  override fun onError() {}

  fun getSamplesFrom(plant: Plant) = Executor
      .execute(GetSamplesFromPlant(RemoteSamplesRepository(), this, plant))

}