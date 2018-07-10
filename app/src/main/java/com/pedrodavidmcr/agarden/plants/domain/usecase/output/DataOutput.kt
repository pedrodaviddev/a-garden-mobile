package com.pedrodavidmcr.agarden.plants.domain.usecase.output

interface DataOutput<in T> {
  fun onLoadData(data: List<T>)
  fun onNoData()
  fun onError()
}