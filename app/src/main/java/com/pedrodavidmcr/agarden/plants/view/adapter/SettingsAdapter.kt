package com.pedrodavidmcr.agarden.plants.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.pedrodavidmcr.agarden.plants.domain.Configuration.BY_HUMIDITY
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHourFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHumidityFragment

class SettingsAdapter(manager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(manager) {
  val byHour = ByHourFragment()
  val byHumidity = ByHumidityFragment()
  override fun getItem(position: Int): androidx.fragment.app.Fragment = when (position) {
    0 -> byHour
    else -> byHumidity
  }

  override fun getCount(): Int = 2

  override fun getPageTitle(position: Int): CharSequence = when (position) {
    0 -> "Hout"
    else -> "Humidity"
  }


  fun setConfiguration(configuration: Int){
    byHour.setConfiguration(configuration)
  }

  fun getConfiguration(currentItem: Int): Int = if (currentItem == 1) BY_HUMIDITY
  else byHour.getConfiguration()


}