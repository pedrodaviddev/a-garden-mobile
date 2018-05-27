package com.pedrodavidmcr.agarden

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private val plantsFragment: PlantsFragment = PlantsFragment()
  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.tab_home -> {
        return@OnNavigationItemSelectedListener true
      }
      R.id.tab_plants -> {
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    setFragment()
  }

  private fun setFragment() =
      supportFragmentManager.beginTransaction()
          .replace(R.id.container, plantsFragment)
          .commitAllowingStateLoss()

}