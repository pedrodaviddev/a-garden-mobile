package com.pedrodavidmcr.agarden.plants.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrodavidmcr.agarden.R
import kotlinx.android.synthetic.main.fragment_plants.*

class PlantsFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.fragment_plants, container, false)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    plantsRecView.adapter = PlantsAdapter()
    plantsRecView.layoutManager = LinearLayoutManager(context)
  }
}
