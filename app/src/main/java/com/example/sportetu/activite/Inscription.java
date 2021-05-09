package com.example.sportetu.activite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportetu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Inscription extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, mdp, prenom, nom;
    Button inscription;
    TextView  redirectionLogin;
    ProgressBar progressBar;

    String userID;
    FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        email = findViewById(R.id.mail_register);
        mdp = findViewById(R.id.mdp_register);
        prenom = findViewById(R.id.prenom_user);
        nom = findViewById(R.id.nom_user);
        redirectionLogin = findViewById(R.id.redirection_login);
        inscription = findViewById(R.id.inscription);
        progressBar = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();

        ConstraintLayout constraintLayout = findViewById(R.id.layoutsign);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String email2= email.getText().toString().trim();
                String mdp2= mdp.getText().toString().trim();
                final String prenom2= prenom.getText().toString();
                final String nom2= nom.getText().toString();



                // conditions sur le mail et mot de pass
                if(TextUtils.isEmpty((email2))){
                    email.setError("une adresse mail est nécessaire pour s'inscrire ");
                    return;
                }

                if( TextUtils.isEmpty(mdp2)){
                    mdp.setError("un mot de passe est nécessaire pour s'inscrire");
                    return;
                }

                if( TextUtils.isEmpty(prenom2)){
                    prenom.setError("un mot de passe est nécessaire pour s'inscrire");
                    return;
                }
                if( TextUtils.isEmpty(nom2)){
                    nom.setError("un mot de passe est nécessaire pour s'inscrire");
                    return;
                }

                if(mdp.length()<5){
                    mdp.setError("il faut un mot de passe de plus de 5 caractères");
                    return;
                }

                //eregistrement de l'utilisateur sur firebase
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email2,mdp2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Inscription.this, "Ton compte est créé, Bienvenue!", Toast.LENGTH_SHORT).show();

                            userID= mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mStore.collection(userID).document("profil_utilisateur");
                            Map<String, Object> profil_utilisateurs = new HashMap<>();

                            profil_utilisateurs.put("mEmail",email2);
                            profil_utilisateurs.put("mNom",nom2);
                            profil_utilisateurs.put("fPrenom",prenom2);
                            profil_utilisateurs.put("nbconnexions",1);
                            profil_utilisateurs.put("nbActivite",0);

                            documentReference.set(profil_utilisateurs).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: profil de l'utilisateur créé" + userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(), dialogActivitychoixSports.class));
                        }else{
                            Toast.makeText(Inscription.this,"une erreur est survenue, veuillez réessayer        "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

        redirectionLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Authentification.class));
            }
        });

    }
}