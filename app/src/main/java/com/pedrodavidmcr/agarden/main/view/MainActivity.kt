package com.pedrodavidmcr.agarden.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pedrodavidmcr.agarden.R
import com.pedrodavidmcr.agarden.home.view.HomeFragment
import com.pedrodavidmcr.agarden.plants.view.fragment.PlantsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private val plantsFragment: PlantsFragment = PlantsFragment()
  private val homeFragment: HomeFragment = HomeFragment()
  private val mOnNavigationItemSelectedListener =
    BottomNavigationView.OnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.tab_home -> {
          title = "Resumen"
          setFragment(homeFragment)
          return@OnNavigationItemSelectedListener true
        }
        R.id.tab_plants -> {
          title = "Lista de plantas"
          setFragment(plantsFragment)
          return@OnNavigationItemSelectedListener true
        }
      }
      false
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    title = "Resumen"
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    setFragment(homeFragment)
  }

  private fun setFragment(fragment: Fragment) =
    supportFragmentManager.beginTransaction()
      .replace(R.id.container, fragment)
      .commitAllowingStateLoss()

}
