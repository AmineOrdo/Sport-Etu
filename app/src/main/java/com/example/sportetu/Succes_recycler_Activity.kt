package com.example.sportetu

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_succes_recycler_.*


class Succes_recycler_Activity : AppCompatActivity() {
    private var TitreList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()
    private var imageList = mutableListOf<Int>()
    var nbActivite: Long = 0
    var nbconnexions:Long = 0
    var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    var mStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succes_recycler_)


        postToList()
        val constraintLayout = findViewById<ConstraintLayout>(R.id.layoutSuccess)
        val animationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

    }

    override fun onStart() {
        super.onStart()
        RecyclerView_Succes.layoutManager=LinearLayoutManager(this)
        RecyclerView_Succes.adapter=AdapterSucces(TitreList, descriptionList, imageList)
    }
    private fun addToList(titre: String, description: String, image: Int){
        TitreList.add(titre)
        descriptionList.add(description)
        imageList.add(image)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //moveTaskToBack(false)
    }
    private fun postToList(){
        var userID: String

        userID = mAuth!!.currentUser!!.uid

        val documentReference = mStore.collection(userID).document("profil_utilisateur")

        documentReference.addSnapshotListener(
            this
        ) { documentSnapshot, e ->

             nbActivite= documentSnapshot!!.get("nbActivite") as Long
            nbconnexions= documentSnapshot!!.get("nbconnexions") as Long

        if(nbconnexions<1 || nbActivite<1) {

            Toast.makeText(this, "ERROR! le compte est corrompu", Toast.LENGTH_SHORT).show()
        }else {


            if (nbconnexions >= 10) {
                addToList(
                    "Bonnes résolutions",
                    "Se connecter 10 fois",
                    R.drawable.ic_baseline_emoji_events_24
                )
            } else {
                addToList(
                    "Bonnes résolutions",
                    "Se connecter 10 fois",
                    R.drawable.ic_baseline_lock_24
                )
            }

            if (nbActivite >= 10) {
                addToList("Amateur", "Valider 10 activités", R.drawable.ic_baseline_emoji_events_24)
            } else {
                addToList("Amateur", "Valider 10 activités", R.drawable.ic_baseline_lock_24)
            }

            if (nbActivite >= 50) {
                addToList(
                    "Sportif assidu",
                    "Valider 50 activités",
                    R.drawable.ic_baseline_emoji_events_24
                )
            } else {
                addToList(
                    "Sportif assidu",
                    "Valider 50 activités",
                    R.drawable.ic_baseline_lock_24
                )
            }

            if (nbActivite >= 100) {
                addToList(
                    "Professionnel",
                    "Valider 100 activités",
                    R.drawable.ic_baseline_emoji_events_24
                )
            } else {
                addToList("Professionnel", "Valider 100 activités", R.drawable.ic_baseline_lock_24)
            }

            if (nbconnexions >= 1000) {
                addToList(
                    "étudiant modèle",
                    "se connecter 1000 fois",
                    R.drawable.ic_baseline_emoji_events_24
                )
            } else {
                addToList(
                    "étudiant modèle",
                    "se connecter 1000 fois",
                    R.drawable.ic_baseline_lock_24
                )
            }

            if (nbActivite >= 1000) {
                addToList(
                    "Légende",
                    "Valider 1000 activités",
                    R.drawable.ic_baseline_emoji_events_24
                )
            } else {
                addToList("Légende", "Valider 1000 activités", R.drawable.ic_baseline_lock_24)
                }

            }

        }

    }

}


