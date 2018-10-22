package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository

class PlantListViewModelFactory(private val plantsRepository: PlantsRepository)
  : ViewModelProvider.NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T =
      PlantListViewModel(plantsRepository) as T
}