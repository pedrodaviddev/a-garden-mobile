package com.pedrodavidmcr.agarden.base

import com.pedrodavidmcr.agarden.main.RemoteIrrigationRepository
import com.pedrodavidmcr.agarden.plants.data.RemotePlantsRepository
import com.pedrodavidmcr.agarden.plants.data.RemoteSamplesRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantConfigurationViewModelFactory
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantDetailViewModelFactory
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantListViewModelFactory

object Injector {
  private fun getPlantRepository(): PlantsRepository =
    RemotePlantsRepository()

  private fun getSamplesRepository(): SamplesRepository =
    RemoteSamplesRepository()

  private fun getIrrigationRepository(): IrrigationRepository =
    RemoteIrrigationRepository()

  fun providePlantDetailViewModelFactory(plantId: Int) =
    PlantDetailViewModelFactory(getPlantRepository(), getSamplesRepository(), plantId)

  fun providePlantConfigurationViewModelFactory(plantId: Int) =
    PlantConfigurationViewModelFactory(getPlantRepository(), plantId)

  fun providePlantListViewModelFactory() = PlantListViewModelFactory(
    getPlantRepository(),
    getIrrigationRepository()
  )
}