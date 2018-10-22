package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class PlantConfigurationViewModelFactory(
    private val plantsRepository: PlantsRepository,
    private val plantId: Int
) : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      PlantConfigurationViewModel(plantsRepository, plantId) as T
}