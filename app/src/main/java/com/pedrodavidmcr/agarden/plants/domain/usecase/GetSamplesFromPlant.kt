package com.pedrodavidmcr.agarden.plants.domain.usecase

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

class GetSamplesFromPlant(private val repository: SamplesRepository,
                          private val output: ListOutput<Sample>,
                          private val plantId: Int) : UseCase {
  override fun execute(): Result {
    try {
      val data = repository.getSamplesFrom(plantId)
      if (data.isEmpty()) return { output.onEmptyList() }
      return { output.onLoadList(data) }
    } catch (e: Exception) {
      return { output.onError() }
    }
  }

}