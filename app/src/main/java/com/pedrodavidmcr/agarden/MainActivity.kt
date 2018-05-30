package com.pedrodavidmcr.agarden

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.pedrodavidmcr.agarden.home.view.HomeFragment
import com.pedrodavidmcr.agarden.plants.data.RemotePlantsRepository
import com.pedrodavidmcr.agarden.plants.view.PlantsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
  private val plantsFragment: PlantsFragment = PlantsFragment()
  private val homeFragment: HomeFragment = HomeFragment()
  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.tab_home -> {
        setFragment(homeFragment)
        return@OnNavigationItemSelectedListener true
      }
      R.id.tab_plants -> {
        setFragment(plantsFragment)
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    setFragment(homeFragment)
    refresh.setOnRefreshListener { plantsFragment.load() }
  }

  private fun setFragment(fragment: Fragment) =
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, fragment)
          .commitAllowingStateLoss()

}
