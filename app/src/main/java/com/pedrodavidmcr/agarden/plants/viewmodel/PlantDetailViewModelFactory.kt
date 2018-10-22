package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository

class PlantDetailViewModelFactory(
    private val plantRepository: PlantsRepository,
    private val samplesRepository: SamplesRepository,
    private val plantId: Int)
  : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      PlantDetailViewModel(plantRepository, samplesRepository, plantId) as T

}