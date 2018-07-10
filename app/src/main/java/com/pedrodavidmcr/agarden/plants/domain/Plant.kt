package com.pedrodavidmcr.agarden.plants.domain

data class Plant(val name: String,
                 val requiredHumidity: Int,
                 val temperature: Double,
                 val sunLight: Double)