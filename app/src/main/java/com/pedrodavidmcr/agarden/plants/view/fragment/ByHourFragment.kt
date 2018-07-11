package com.pedrodavidmcr.agarden.plants.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.fragment_by_hour.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ByHourFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?)
      : View? = inflater.inflate(R.layout.fragment_by_hour, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    afterSunset.onClick {
      afterSunset.isChecked = !afterSunset.isChecked
    }
    onSunrise.onClick {
      onSunrise.isChecked = !onSunrise.isChecked
    }
  }

}
