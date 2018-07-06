package com.pedrodavidmcr.agarden.base.view

interface ListView<in T> {
  fun onListLoaded(list: List<T>)
}