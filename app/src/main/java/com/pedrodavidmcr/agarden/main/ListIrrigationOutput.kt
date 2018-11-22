package com.pedrodavidmcr.agarden.main

import com.pedrodavidmcr.agarden.main.model.Irrigation
import com.pedrodavidmcr.agarden.plants.domain.usecase.output.ListOutput

interface ListIrrigationOutput{
  fun onLoadListIrrigation(list: List<Irrigation>)
}