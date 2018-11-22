package com.pedrodavidmcr.agarden.plants.domain.repository

import com.pedrodavidmcr.agarden.main.model.Irrigation

interface IrrigationRepository {
  fun getLastIrrigationList(): List<Irrigation>
  fun getIrrigationNumberOfToday(): Int
}