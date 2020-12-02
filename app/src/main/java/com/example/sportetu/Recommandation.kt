package com.example.sportetu

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class Recommandation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommandation_test)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun recommandation(dateSport: String, heureDepart: String, heureArrive: String ): ListenerRegistration {
        //initialisation firebase
        var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
        var mStore = FirebaseFirestore.getInstance()

        //IDuser
        var userID:String
        userID = mAuth!!.currentUser!!.uid

        //Recuperation des 3 sports
        val documentReference = mStore.collection(userID).document("Sport_utilisateur")

        var rmd : Int

        val registration = documentReference.addSnapshotListener(this) { documentSnapshot, e ->
            var sportPref1 = documentSnapshot!!.get("Sportpref1")
            var sportPref2 = documentSnapshot!!.get("Sportpref2")
            var sportPref3 = documentSnapshot!!.get("Sportpref3")




            val list = (1..3).filter { it % 2 == 0 }
            rmd = list.random()


            if(rmd == 1){
                return@addSnapshotListener (sportPref1 as Unit)
            }else if(rmd == 2){
                return@addSnapshotListener (sportPref2 as Unit)
            }else{
                return@addSnapshotListener (sportPref3 as Unit)
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

        return (registration)



    }
}


