package com.pedrodavidmcr.agarden.plants.presenter

import com.pedrodavidmcr.agarden.base.executor.Executor
import com.pedrodavidmcr.agarden.plants.data.RemotePlantsRepository
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.usecase.GetAllPlants
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput
import com.pedrodavidmcr.agarden.base.view.ListView

class PlantsPresenter(private val view: ListView<Plant>) : ListOutput<Plant> {
  override fun onLoadList(list: List<Plant>) = view.onListLoaded(list)


  override fun onEmptyList() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onError() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  fun getPlants() = Executor.execute(GetAllPlants(RemotePlantsRepository(), this))

}