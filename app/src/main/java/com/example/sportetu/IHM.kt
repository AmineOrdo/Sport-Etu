package com.example.sportetu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.Toast
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

 class IHM : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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

     override fun onNavigationItemSelected(p0: MenuItem): Boolean {
         TODO("Not yet implemented")
     }

    lateinit var toolbar: Toolbar
     lateinit var drawerLayout: DrawerLayout
     lateinit var navView: NavigationView
     override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
         super.onCreate(savedInstanceState, persistentState)
         setContentView(R.layout.activity_main)

         toolbar = findViewById(R.id.toolbar)
         setSupportActionBar(toolbar)

         drawerLayout = findViewById(R.id.drawer_layout)
         navView = findViewById(R.id.navigation_drawer)

         var toggle = ActionBarDrawerToggle(
             this,
             drawerLayout,
             0,
             0
         )
         drawerLayout.addDrawerListener(toggle)
         toggle.syncState()
     }
         override fun onBackPressed() {
             if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                 drawerLayout.closeDrawer(GravityCompat.START)
             } else {
                 super.onBackPressed()
             }
         }


     private fun setSupportActionBar(toolbar: Toolbar?) {
         TODO("Not yet implemented")
     }

 }

