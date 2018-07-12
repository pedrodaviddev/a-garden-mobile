package com.pedrodavidmcr.agarden.plants.domain.usecase

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class UpdateConfigurationOfPlant(private val repository: PlantsRepository,
                                 private val plant: Plant): UseCase {
  override fun execute(): Result {
    repository.updatePlant(plant)
    return {}
  }
}