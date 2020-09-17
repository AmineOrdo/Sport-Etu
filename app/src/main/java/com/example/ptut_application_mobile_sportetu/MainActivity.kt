package com.example.ptut_application_mobile_sportetu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ProgressionFragment = ProgressionFragment()
        val SuccesFragment = SuccesFragment()
        val EntrainementFragment = EntrainementFragment()

        makeCurrentFragment(ProgressionFragment)

        bottom_navigation.setOnNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_progression->makeCurrentFragment(ProgressionFragment)
                R.id.nav_succes->makeCurrentFragment(SuccesFragment)
                R.id.nav_entrainement->makeCurrentFragment(EntrainementFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container,fragment)
            commit()
        }
}