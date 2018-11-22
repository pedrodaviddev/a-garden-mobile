package com.pedrodavidmcr.agarden.plants.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.main.GetListLastIrrigation
import com.pedrodavidmcr.agarden.main.GetNumberIrrigationToday
import com.pedrodavidmcr.agarden.main.ListIrrigationOutput
import com.pedrodavidmcr.agarden.main.model.Irrigation
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository
import com.pedrodavidmcr.agarden.plants.domain.repository.PlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetAllPlants
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.AnyOutput
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

class MainViewModel(
  plantsRepository: PlantsRepository,
  private val irrigationRepository: IrrigationRepository
) : ViewModel(), ListOutput<Plant>, ListIrrigationOutput, AnyOutput<Int> {

  var plantList: MutableLiveData<List<Plant>> = MutableLiveData()
  var irrigationList: MutableLiveData<List<Pair<Irrigation, Plant>>> = MutableLiveData()
  var numberOfIrrigationToday: MutableLiveData<Int> = MutableLiveData()

  init {
    Executor.execute(GetAllPlants(plantsRepository, this))
    Executor.execute(GetNumberIrrigationToday(irrigationRepository, this))
  }

  override fun onLoad(any: Int) {
    numberOfIrrigationToday.value = any
  }

  override fun onLoadListIrrigation(list: List<Irrigation>) {
    irrigationList.value = list.map { irrigation ->
      Pair(irrigation, plantList.value!!.first { it.id == irrigation.plantId })
    }
  }

  override fun onLoadList(list: List<Plant>) {
    plantList.value = list
    Executor.execute(GetListLastIrrigation(irrigationRepository, this))
  }

  override fun onEmptyList() {
    plantList.value = emptyList()
  }

  override fun onError() {
    plantList.value = emptyList()
  }
}