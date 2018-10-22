package com.pedrodavidmcr.agarden.plants.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.base.Injector
import com.pedrodavidmcr.agarden.plants.domain.Plant
import com.pedrodavidmcr.agarden.plants.view.activity.PlantDetailActivity
import com.pedrodavidmcr.agarden.plants.view.adapter.PlantsAdapter
import com.pedrodavidmcr.agarden.plants.viewmodel.PlantListViewModel
import kotlinx.android.synthetic.main.fragment_plants.*
import kotlinx.android.synthetic.main.items_plants.*
import org.jetbrains.anko.intentFor

class PlantsFragment : Fragment() {
  private lateinit var viewModel: PlantListViewModel
  private val onElementClickListener: (TextView, CardView, ImageView, Plant) -> Unit by lazy {
    { text: TextView, card: CardView, image: ImageView, plant: Plant ->
      val arrayOfTransitionNames = arrayOf<Pair<View, String>>(
          Pair.create(card, ViewCompat.getTransitionName(card)),
          Pair.create(image, ViewCompat.getTransitionName(image)),
          Pair.create(text, ViewCompat.getTransitionName(text)))
      val bundle: Bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
          activity!!, *arrayOfTransitionNames)
          .toBundle()!!

      val intent = activity!!.intentFor<PlantDetailActivity>(
          "transitionRoot" to ViewCompat.getTransitionName(card),
          "transitionImage" to ViewCompat.getTransitionName(image),
          "transitionName" to ViewCompat.getTransitionName(plantName),
          "plantId" to plant.id
      )
      startActivity(intent, bundle)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val factory = Injector.providePlantListViewModelFactory()
    viewModel = ViewModelProviders.of(this, factory).get(PlantListViewModel::class.java)
    return inflater.inflate(R.layout.fragment_plants, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) = initPlantList()

  private fun initPlantList() {
    plantsRecView.layoutManager = LinearLayoutManager(context)
    val adapter = PlantsAdapter(onElementClickListener)
    viewModel.plantList.observe(this, Observer { listPlant ->
      adapter.list = listPlant
      adapter.notifyDataSetChanged()
    })
    plantsRecView.adapter = adapter
  }
}
