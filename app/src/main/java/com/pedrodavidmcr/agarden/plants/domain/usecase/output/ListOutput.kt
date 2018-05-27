package com.pedrodavidmcr.agarden.plants.domain.usecase.output

interface ListOutput<in T> {
  fun onLoadList(list: List<T>)
  fun onEmptyList()
  fun onError()
}