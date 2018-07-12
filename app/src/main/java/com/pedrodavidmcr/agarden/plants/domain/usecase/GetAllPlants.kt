package com.pedrodavidmcr.agarden.plants.domain.usecase

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

class GetAllPlants(private val repository: PlantsRepository,
                   private val output: ListOutput<Plant>) : UseCase {
  override fun execute(): Result {
    try {
      val list = repository.getAllPlants()
      return { output.onLoadList(list) }
    } catch (e: Exception) {
      return { throw e }
    }
  }
}