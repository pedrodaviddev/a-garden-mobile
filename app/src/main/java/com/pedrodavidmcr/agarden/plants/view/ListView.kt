package com.pedrodavidmcr.agarden.plants.view

interface ListView<in T> {
  fun onListLoaded(list: List<T>)
}