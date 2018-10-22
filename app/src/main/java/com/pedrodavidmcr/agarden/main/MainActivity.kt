package com.pedrodavidmcr.agarden.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.home.view.HomeFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.PlantsFragment
import kotlinx.android.synthetic.main.activity_main.*

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
  }

  private fun setFragment(fragment: androidx.fragment.app.Fragment) =
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, fragment)
          .commitAllowingStateLoss()

}
