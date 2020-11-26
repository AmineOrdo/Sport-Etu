package com.example.sportetu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class choix3Sports extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button buttonNext2;
    ImageView badminton,football,basket,checkfoot,checkbad,checkbask;
    FirebaseFirestore mStore;
    String userID;
    int compteur=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix3_sports);

        //liste sports à choisir

        buttonNext2 = findViewById(R.id.next2);

        //images
        badminton=findViewById(R.id.badminton);
        football=findViewById(R.id.football);
        basket=findViewById(R.id.basketball);

        //check bouttons
        checkbad=findViewById(R.id.checkbad);
        checkfoot=findViewById(R.id.checkfoot);
        checkbask=findViewById(R.id.checkbask);


        mAuth = FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();



        badminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbad.isShown()){
                    checkbad.setVisibility(View.GONE);
                    compteur=compteur-1;
                }else {
                    checkbad.setVisibility(View.VISIBLE);
                    compteur=compteur+1;
                }
            }
        });

        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkfoot.isShown()){
                    checkfoot.setVisibility(View.GONE);
                    compteur=compteur-1;
                }else {
                    checkfoot.setVisibility(View.VISIBLE);
                    compteur=compteur+1;
                }
            }
        });

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbask.isShown()){
                    checkbask.setVisibility(View.GONE);
                    compteur=compteur-1;
                }else {
                    checkbask.setVisibility(View.VISIBLE);
                    compteur=compteur+1;
                }
            }
        });



    buttonNext2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (compteur==3) {


                String[] tab = new String[10];

                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = mStore.collection(userID).document("Sport_utilisateur");

                Map<String, Object> Sports_utilisateurs = new HashMap<>();
                int i = 0;
                while ( i < 3) {

                    if (checkbad.isShown()) {
                        tab[i] = "badminton";
                        continue ;
                    }
                    i=i+1;
                    if (checkfoot.isShown()) {
                        tab[i] = "football";
                        continue;
                    }
                    i=i+1;
                    if (checkbask.isShown()) {
                        tab[i] = "basketball";
                        continue;
                    }
                }

                Sports_utilisateurs.put("Sportpref1", tab[0]);
                Sports_utilisateurs.put("Sportpref2", tab[1]);
                Sports_utilisateurs.put("Sportpref3", tab[2]);

                documentReference.set(Sports_utilisateurs).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: choix des sports de user effectuer!!!" + userID);
                    }
                });
                startActivity(new Intent(getApplicationContext(), IHM.class));
            }else {
                Toast.makeText(choix3Sports.this, "Veuillez selectionner exactement 3 sports   ", Toast.LENGTH_SHORT).show();
            }
        }

    });
}


    }



