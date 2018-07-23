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
import android.widget.TextView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.view.ListView
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.presenter.PlantsPresenter
import com.pedrodavidmcr.agarden.plants.view.activity.PlantsDetailsActivity
import com.pedrodavidmcr.agarden.plants.view.adapter.PlantsAdapter
import com.pedrodavidmcr.agarden.plants.view.toBundle
import kotlinx.android.synthetic.main.fragment_plants.*
import kotlinx.android.synthetic.main.items_plants.*
import org.jetbrains.anko.support.v4.intentFor

class PlantsFragment : Fragment(), ListView<Plant> {
  private val presenter: PlantsPresenter by lazy { PlantsPresenter(this) }
  private val trigger: (TextView, CardView, ImageView, Plant) -> Unit by lazy {
    { text: TextView, card: CardView, image: ImageView, plant: Plant ->
      val array = arrayOf<Pair<View, String>>(
          Pair.create(card, ViewCompat.getTransitionName(card)),
          Pair.create(image, ViewCompat.getTransitionName(image)),
          Pair.create(text, ViewCompat.getTransitionName(text)))
      val bundle: Bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
          activity!!, *array)
          .toBundle()!!

      val intent = intentFor<PlantsDetailsActivity>(
          "transitionRoot" to ViewCompat.getTransitionName(card),
          "transitionImage" to ViewCompat.getTransitionName(image),
          "transitionName" to ViewCompat.getTransitionName(plantName)
      )
      intent.putExtras(plant.toBundle())
      startActivity(intent, bundle)
    }
  }

  override fun onListLoaded(list: List<Plant>) {
    plantsRecView.adapter = PlantsAdapter(trigger).also {
      it.list = list
    }
    plantsRecView.layoutManager = LinearLayoutManager(context)
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
