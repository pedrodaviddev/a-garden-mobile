package com.pedrodavidmcr.agarden.plants.domain.repository

import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.domain.Sample

interface SamplesRepository {
  fun getSamplesFrom(plantId: Int): List<Sample>
}