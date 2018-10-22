package com.pedrodavidmcr.agarden.plants.domain.usecase

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.AnyOutput

class GetPlant(
    private val repository: PlantsRepository,
    private val output: AnyOutput<Plant>,
    private val plantId: Int
) : UseCase {
  override fun execute(): Result {
    val plant = repository.getPlant(plantId)
    return { output.onLoad(plant) }
  }
}