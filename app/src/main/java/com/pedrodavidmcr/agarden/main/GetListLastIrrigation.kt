package com.pedrodavidmcr.agarden.main

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository

class GetListLastIrrigation(
  private val repository: IrrigationRepository,
  private val output: ListIrrigationOutput
) : UseCase {
  override fun execute(): Result {
    val list = repository.getLastIrrigationList()
    return { output.onLoadListIrrigation(list.reversed()) }
  }


}