package com.pedrodavidmcr.agarden.plants.view.fragment

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pedrodavidmcr.agarden.main.MainActivity
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.view.ListView
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.presenter.PlantsPresenter
import com.pedrodavidmcr.agarden.plants.view.adapter.PlantsAdapter
import com.pedrodavidmcr.agarden.plants.view.activity.PlantsDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_plants.*
import org.jetbrains.anko.support.v4.intentFor

class PlantsFragment : Fragment(), ListView<Plant> {
  val presenter: PlantsPresenter by lazy { PlantsPresenter(this) }
  val trigger: (CardView, ImageView, Plant) -> Unit by lazy {
    { card: CardView, image: ImageView, plant: Plant ->
      val array = arrayOf<Pair<View, String>>(
          Pair.create(card, ViewCompat.getTransitionName(card)),
          Pair.create(image, ViewCompat.getTransitionName(image)))
      val bundle: Bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
          activity!!, *array)
          .toBundle()!!


      startActivity(intentFor<PlantsDetailsActivity>(
          "transitionRoot" to ViewCompat.getTransitionName(card),
          "transitionImage" to ViewCompat.getTransitionName(image)
      ), bundle)
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
