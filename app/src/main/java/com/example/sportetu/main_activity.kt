package com.example.sportetu

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_progression.*
import kotlinx.android.synthetic.main.header.*


class main_activity : AppCompatActivity()  {
    //  navigation vers les différentes pages

    override fun onBackPressed() {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
        var mStore = FirebaseFirestore.getInstance()

        val constraintLayout = findViewById<FrameLayout>(R.id.fragment_container)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        val ProgressionFragment = ProgressionFragment()
        val SuccesFragment = SuccesFragment()
        val EntrainementFragment = EntrainementFragment()

        makeCurrentFragment(EntrainementFragment)

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
        /*
        val documentReference = mStore.collection(userID).document("profil_utilisateur")




            documentReference.addSnapshotListener(
                this
            ) { documentSnapshot, e ->
                prenom_user?.text = documentSnapshot!!.getString("fPrenom")
                nom_user?.text = documentSnapshot!!.getString("mNom")
                email_user?.text = documentSnapshot!!.getString("mEmail")

            }

*/
        val docRef: DocumentReference = mStore.collection(userID).document("profil_utilisateur")

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document!!.exists()) {

                    prenom_user?.text = document!!.getString("fPrenom")
                    nom_user?.text = document!!.getString("mNom")
                    email_user?.text = document!!.getString("mEmail")
                    //nom_stats?.text = document!!.getString("fPrenom") +" "+ document!!.getString("mNom")
                    conn?.text = "connexions: "+document!!.get("nbconnexions").toString()
                    nbacti?.text ="activités terminées: "+document!!.get("nbActivite").toString()



                } else {

                }
            } else {

            }
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







