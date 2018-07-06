package com.pedrodavidmcr.agarden.plants.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHourFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.ByHumidityFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.ByLightFragment

class SettingsAdapter(manager: FragmentManager): FragmentStatePagerAdapter(manager){
  override fun getItem(position: Int): Fragment = when(position){
    0 -> ByLightFragment()
    1 -> ByHourFragment()
    else -> ByHumidityFragment()
  }

  override fun getCount(): Int = 3

  override fun getPageTitle(position: Int): CharSequence = when(position){
    0 -> "Light"
    1 -> "Hour"
    else -> "Humidity"
  }

}