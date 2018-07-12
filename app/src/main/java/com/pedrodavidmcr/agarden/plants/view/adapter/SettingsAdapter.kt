package com.pedrodavidmcr.agarden.plants.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pedrodavidmcr.agarden.plants.domain.Configuration.BY_HUMIDITY
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHourFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHumidityFragment

class SettingsAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
  val byHour = ByHourFragment()
  val byHumidity = ByHumidityFragment()
  override fun getItem(position: Int): Fragment = when (position) {
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