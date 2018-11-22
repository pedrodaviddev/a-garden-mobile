package com.pedrodavidmcr.agarden.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.Injector
import com.pedrodavidmcr.agarden.main.view.adapter.IrrigationAdapter
import com.pedrodavidmcr.agarden.plants.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
  private val adapter: IrrigationAdapter = IrrigationAdapter()

  private val viewModel: MainViewModel by lazy {
    val factory = Injector.providePlantListViewModelFactory()
    activity?.run {
      ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
    }!!
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? =
    inflater.inflate(R.layout.fragment_home, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    irrigationList.adapter = adapter
    irrigationList.layoutManager = LinearLayoutManager(context)
    viewModel.plantList.observe(this, Observer {
      connectedPlants.text = if (it.size > 1) "${it.size} plantas conectadas" else {
        "1 planta conectada"
      }
    })
    viewModel.irrigationList.observe(this, Observer {
      adapter.data = it
      adapter.notifyDataSetChanged()
    })
    viewModel.numberOfIrrigationToday.observe(this, Observer {
      totalIrrigations.text = "$it riegos hoy"
    })
  }
}
