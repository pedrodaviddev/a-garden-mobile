package com.pedrodavidmcr.agarden.plants.view

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pedrodavidmcr.agarden.MainActivity
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.presenter.PlantsPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_plants.*
import org.jetbrains.anko.support.v4.intentFor

class PlantsFragment : Fragment(), ListView<Plant> {
  val presenter: PlantsPresenter by lazy { PlantsPresenter(this) }
  val trigger: (View, Plant) -> Unit by lazy {
    { view: View, plant: Plant ->
      val string = ViewCompat.getTransitionName(view)
      val bundle: Bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
          activity!!,
          view,
          string
      ).toBundle()!!
      startActivity(intentFor<PlantsDetailsActivity>("transitionName" to string), bundle)
    }
  }

  override fun onListLoaded(list: List<Plant>) {
    plantsRecView.adapter = PlantsAdapter(trigger).also {
      it.list = list
    }
    plantsRecView.layoutManager = LinearLayoutManager(context)
    (activity as MainActivity).refresh.isRefreshing = false
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.fragment_plants, container, false)


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    load()
  }

  fun load() {
    presenter.getPlants()
  }
}
