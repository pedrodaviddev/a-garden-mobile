package com.pedrodavidmcr.agarden.plants.presenter

import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.plants.data.RemotePlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.usecase.UpdateConfigurationOfPlant

class PlantConfigurationPresenter {
  fun updateConfigurationOf(plant: Plant) = Executor
      .execute(UpdateConfigurationOfPlant(RemotePlantsRepository(),plant))
}