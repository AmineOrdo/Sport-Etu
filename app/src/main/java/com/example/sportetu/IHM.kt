package com.example.sportetu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_entrainement.*
import kotlinx.android.synthetic.main.header.*
import java.util.*


class IHM : AppCompatActivity()  {
     //  navigation vers les différentes pages



     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
         var mStore = FirebaseFirestore.getInstance()

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

         top_navigation.setOnMenuItemClickListener {
             when (it.itemId) {
                 R.id.nav_menu -> drawer_layout.openDrawer(GravityCompat.START)

             }
             true
         }


         navigation_drawer.setNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.logout -> deconnexion()
                 //R.id.mode_nuit->
                 //R.id.settings->
             }
             true
         }

         //afficher les infos du profil de l'utilisateur sur la fenetre


         var userID: String

         userID = mAuth!!.currentUser!!.uid
         val documentReference = mStore.collection(userID).document("profil_utilisateur")



         documentReference.addSnapshotListener(
             this
         ) { documentSnapshot, e ->
             prenom_user.text = documentSnapshot!!.getString("fPrenom")
             nom_user.text = documentSnapshot!!.getString("mNom")
             email_user.text = documentSnapshot!!.getString("mEmail")
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







