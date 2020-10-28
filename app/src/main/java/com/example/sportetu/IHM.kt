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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class IHM : AppCompatActivity()  {
     // bottom navigation
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)



         val ProgressionFragment = ProgressionFragment()
         val SuccesFragment = SuccesFragment()
         val EntrainementFragment = EntrainementFragment()

         makeCurrentFragment(ProgressionFragment)


         bottom_navigation.setOnNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.nav_progression -> makeCurrentFragment(ProgressionFragment)
                 R.id.nav_succes -> makeCurrentFragment(SuccesFragment)
                 R.id.nav_entrainement -> makeCurrentFragment(EntrainementFragment)
             }
             true
         }

     }

     private fun makeCurrentFragment(fragment: Fragment) =
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fragment_container, fragment)
             commit()
         }

     // top navigation
     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.top_navigation, menu)
         return super.onCreateOptionsMenu(menu)
     }




    lateinit var hamenu: Button
     lateinit var drawerLayout: DrawerLayout
     lateinit var navView: NavigationView

     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
         super.onCreate(savedInstanceState, persistentState)
         setContentView(R.layout.activity_main)
         drawerLayout = findViewById(R.id.drawer_layout)
         navView = findViewById(R.id.navigation_drawer)

         hamenu = findViewById(R.id.nav_menu)
        hamenu.setOnClickListener{
            drawerLayout.openDrawer(drawerLayout)
        }


     }



 }



