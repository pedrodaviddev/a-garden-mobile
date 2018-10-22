package com.pedrodavidmcr.agarden.plants.domain.usecase.output

interface AnyOutput<T> {
  fun onLoad(any: T)
}