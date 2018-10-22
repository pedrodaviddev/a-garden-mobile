package com.pedrodavidmcr.agarden.plants.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.domain.Configuration.BY_TIME_SUNRISE
import com.pedrodavidmcr.agarden.plants.domain.Configuration.BY_TIME_SUNSET
import kotlinx.android.synthetic.main.fragment_by_hour.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ByHourFragment : androidx.fragment.app.Fragment() {
  var conf = 0
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?)
      : View? = inflater.inflate(R.layout.fragment_by_hour, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    when (conf) {
      BY_TIME_SUNSET -> afterSunset.isChecked = true
      BY_TIME_SUNRISE -> onSunrise.isChecked = true
      else -> {
        onSunrise.isChecked = true
        afterSunset.isChecked = true
      }
    }
    afterSunset.onClick {
      afterSunset.isChecked = !afterSunset.isChecked
    }
    onSunrise.onClick {
      onSunrise.isChecked = !onSunrise.isChecked
    }
  }

  fun getConfiguration(): Int {
    var conf = 0
    if (afterSunset.isChecked) conf += BY_TIME_SUNSET
    if (onSunrise.isChecked) conf += BY_TIME_SUNRISE
    return conf
  }

  fun setConfiguration(configuration: Int) {
    conf = configuration
  }

}
