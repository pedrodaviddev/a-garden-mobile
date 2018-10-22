package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetAllPlants
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

class PlantListViewModel(plantsRepository: PlantsRepository) : ViewModel(), ListOutput<Plant> {

  var plantList: MutableLiveData<List<Plant>> = MutableLiveData()

  init {
    Executor.execute(GetAllPlants(plantsRepository, this))
  }

  override fun onLoadList(list: List<Plant>) {
    plantList.value = list
  }

  override fun onEmptyList() {
    plantList.value = emptyList()
  }

  override fun onError() {
    plantList.value = emptyList()
  }
}