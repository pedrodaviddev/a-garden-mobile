package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.SamplesRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetPlant
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetSamplesFromPlant
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.AnyOutput
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

class PlantDetailViewModel(
    private val plantRepository: PlantsRepository,
    private val samplesRepository: SamplesRepository,
    private val plantId: Int)
  : ViewModel(), AnyOutput<Plant>, ListOutput<Sample> {

  var plant: MutableLiveData<Plant> = MutableLiveData()
  var samples: MutableLiveData<List<Sample>> = MutableLiveData()
  val loading: MutableLiveData<Boolean> = MutableLiveData()

  init {
    load()
  }

  override fun onLoad(any: Plant) {
    plant.value = any
    loading.value = false
  }

  override fun onLoadList(list: List<Sample>) {
    samples.value = list
    loading.value = false
  }

  override fun onEmptyList() {
    samples.value = emptyList()
  }

  override fun onError() {
    samples.value = emptyList()
  }

  fun load() {
    Executor.execute(GetSamplesFromPlant(samplesRepository, this, plantId))
    Executor.execute(GetPlant(plantRepository, this, plantId))
  }
}
