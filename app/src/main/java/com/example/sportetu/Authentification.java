package com.example.sportetu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email, mdp;
    Button connexion;
    TextView mdpOublie, redirectionRegister;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        email= findViewById(R.id.mail_login);
        mdp= findViewById(R.id.mdp_login);
        connexion= findViewById(R.id.connexion);
        mdpOublie=findViewById(R.id.mdp_forget);
        redirectionRegister=findViewById(R.id.redirection_register);
        progressBar=findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2 = email.getText().toString().trim();
                String mdp2 = email.getText().toString().trim();

                // conditions sur le mail et mot de pass
                if (TextUtils.isEmpty((email2))) {
                    email.setError("il faut renseigner ton adresse mail ");
                    return;
                }

                if (TextUtils.isEmpty(mdp2)) {
                    mdp.setError("il faut renseigner ton mot de passe");
                    return;
                }

                if (mdp.length() < 5) {
                    mdp.setError("mot de passe incorrect");
                    return;
                }

                //authentification de l'utilisateur sur firebase
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email2,mdp2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Authentification.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),IHM.class));
                        }
                        else{
                            Toast.makeText(Authentification.this,"identifiants incorrects   "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        redirectionRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Inscription.class));
            }
        });
    }
}