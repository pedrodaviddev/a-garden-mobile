package com.pedrodavidmcr.agarden.main

import com.pedrodavidmcr.agarden.base.domain.usecase.UseCase
import com.pedrodavidmcr.agarden.base.executor.Result
import com.pedrodavidmcr.agarden.plants.domain.repository.IrrigationRepository
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.AnyOutput

class GetNumberIrrigationToday(
  private val repository: IrrigationRepository,
  private val output: AnyOutput<Int>) : UseCase {
  override fun execute(): Result {
    val number = repository.getIrrigationNumberOfToday()
    return { output.onLoad(number) }
  }
}