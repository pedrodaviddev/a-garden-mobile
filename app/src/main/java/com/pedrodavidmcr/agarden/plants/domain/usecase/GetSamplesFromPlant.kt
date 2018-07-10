package com.pedrodavidmcr.agarden.plants.domain.usecase

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.DataOutput

class GetSamplesFromPlant(private val repository: SamplesRepository,
                          private val output: DataOutput<Sample>,
                          private val plant: Plant) : UseCase {
  override fun execute(): Result {
    try {
      val data = repository.getSamplesFrom(plant)
      if (data.isEmpty()) return { output.onNoData() }
      return { output.onLoadData(data) }
    } catch (e: Exception) {
      return { output.onError() }
    }
  }

}