package com.example.sportetu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class IHM : AppCompatActivity() {
    //fonction pour la bottom navigation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ProgressionFragment = ProgressionFragment()
        val SuccesFragment = SuccesFragment()
        val EntrainementFragment = EntrainementFragment()

        makeCurrentFragment(ProgressionFragment)

        bottom_navigation.setOnNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_progression ->makeCurrentFragment(ProgressionFragment)
                R.id.nav_succes ->makeCurrentFragment(SuccesFragment)
                R.id.nav_entrainement ->makeCurrentFragment(EntrainementFragment)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container,fragment)
            commit()
        }
    //fonction pour le top navigation
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_navigation,menu)
        return super.onCreateOptionsMenu(menu)
    }


}