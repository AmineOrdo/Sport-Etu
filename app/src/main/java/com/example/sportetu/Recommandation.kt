package com.example.sportetu

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.recommandation_test.*

class Recommandation : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommandation_test)
        recommandation("fezze","fezef","zfeaqf")
    }
    @RequiresApi(Build.VERSION_CODES.O)

        fun recommandation(dateSport: String, heureDepart: String, heureArrive: String) {
            //initialisation firebase
            var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
            var mStore = FirebaseFirestore.getInstance()


            //IDuser
            var userID: String
            userID = mAuth!!.currentUser!!.uid

            //Recuperation des 3 sports
            val documentReference = mStore.collection(userID).document("Sport_utilisateur")

            var rmd: Int


                documentReference.addSnapshotListener(this) { documentSnapshot, e ->
                    var sportPref1  = documentSnapshot!!.getString("Sportpref1").toString()
                    var sportPref2 = documentSnapshot!!.getString("Sportpref2").toString()
                    var sportPref3  = documentSnapshot!!.getString("Sportpref3").toString()


                    val rmd = (1..3).random()

                    if (rmd==1) {
                        testRecommandation.text=sportPref1
                    } else if(rmd==2){
                        testRecommandation.text=sportPref2
                    }else if (rmd==3){
                        testRecommandation.text=sportPref3
                    }



                    /*
                                    if(LocalTime.parse(heureDepart) > LocalTime.parse("18:00:00")){
                                            return 0
                                    }
                                    else{

                                    }


                                    if(historique.sportPref1 > 3){
                                        return@addSnapshotListener (sportPref2 as Unit)
                                        return@addSnapshotListener (sportPref3 as Unit)
                                    }
                        */
                }




        }



}




