package com.example.sportetu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
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


      navigation_drawer.setNavigationItemSelectedListener {
          when(it.itemId){
              R.id.logout->deconnexion()
              //R.id.mode_nuit->
              //R.id.settings->
          }
          true
      }


     }
     private fun makeCurrentFragment(fragment: Fragment) =
         supportFragmentManager.beginTransaction().apply {
             replace(R.id.fragment_container, fragment)
             commit()
         }


    private fun deconnexion(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, Authentification::class.java))
        finish()
    }

 }







