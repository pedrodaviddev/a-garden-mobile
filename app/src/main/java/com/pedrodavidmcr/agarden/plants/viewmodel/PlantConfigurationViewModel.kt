package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetPlant
import com.pedrodavidmcr.agarden.plants.domain.usecase.UpdateConfigurationOfPlant
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.AnyOutput

class PlantConfigurationViewModel(
    private val plantsRepository: PlantsRepository,
    plantId: Int
) : ViewModel(), AnyOutput<Plant> {

  val plant: MutableLiveData<Plant> = MutableLiveData()

  init {
    Executor execute GetPlant(plantsRepository, this, plantId)
  }

  fun updatePlantConfiguration() {
    Executor execute UpdateConfigurationOfPlant(plantsRepository, plant.value!!)
  }

  override fun onLoad(any: Plant) {
    plant.value = any
  }

}