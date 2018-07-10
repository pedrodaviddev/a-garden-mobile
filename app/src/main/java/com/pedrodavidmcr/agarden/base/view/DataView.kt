package com.pedrodavidmcr.agarden.base.view

interface DataView<in T> {
  fun onDataLoaded(data: List<T>)
}