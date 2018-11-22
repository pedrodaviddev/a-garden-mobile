package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class PlantListViewModelFactory(
  private val plantsRepository: PlantsRepository,
  private val irrigationRepository: IrrigationRepository
) :
  ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
    MainViewModel(plantsRepository, irrigationRepository) as T
}