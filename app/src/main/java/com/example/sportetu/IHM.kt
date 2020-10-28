package com.example.sportetu

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class IHM : AppCompatActivity()  {
     //  navigation vers les différentes pages
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)



         val ProgressionFragment = ProgressionFragment()
         val SuccesFragment = SuccesFragment()
         val EntrainementFragment = EntrainementFragment()

         makeCurrentFragment(ProgressionFragment)

//pour ouvrir les pages en cliquant sur les boutons en bas
         bottom_navigation.setOnNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.nav_progression -> makeCurrentFragment(ProgressionFragment)
                 R.id.nav_succes -> makeCurrentFragment(SuccesFragment)
                 R.id.nav_entrainement -> makeCurrentFragment(EntrainementFragment)
             }
             true
         }
         // pour ouvrir la navigation drawer en cliquant sur le bouton menu en haut
         lateinit var drawerLayout: DrawerLayout
         drawerLayout = findViewById(R.id.drawer_layout)
         top_navigation.setOnMenuItemClickListener {
             when(it.itemId){
                 R.id.nav_menu-> drawerLayout.openDrawer(GravityCompat.START)

             }
             true
         }

     }
     private fun makeCurrentFragment(fragment: Fragment) =
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fragment_container, fragment)
             commit()
         }


 }







