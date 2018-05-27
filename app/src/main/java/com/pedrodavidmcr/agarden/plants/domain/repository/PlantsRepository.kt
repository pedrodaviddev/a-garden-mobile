package com.pedrodavidmcr.agarden.plants.domain.repository

import com.pedrodavidmcr.agarden.plants.domain.Plant

interface PlantsRepository {
  fun getAllPlants(): List<Plant>
}