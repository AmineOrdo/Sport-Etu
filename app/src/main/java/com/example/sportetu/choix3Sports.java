package com.example.sportetu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class choix3Sports extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    Button buttonNext2;
    FirebaseFirestore mStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix3_sports);

        ConstraintLayout constraintLayout = findViewById(R.id.layoutchoix);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        final Spinner spinner1= findViewById(R.id.Sportpref1_selector);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Listsport, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        final Spinner spinner2= findViewById(R.id.Sportpref2_selector);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Listsport, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        final Spinner spinner3= findViewById(R.id.Sportpref3_selector);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Listsport, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);

buttonNext2=findViewById(R.id.next2);

    buttonNext2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        if(spinner1 != null && spinner1.getSelectedItem() !=null ||spinner2 != null && spinner2.getSelectedItem() !=null ||spinner3 != null && spinner3.getSelectedItem() !=null  ){

            mAuth = FirebaseAuth.getInstance();
                userID = mAuth.getCurrentUser().getUid();
            mStore=FirebaseFirestore.getInstance();
                DocumentReference documentReference = mStore.collection(userID).document("Sport_utilisateur");

                Map<String, Object> Sports_utilisateurs = new HashMap<>();

                Sports_utilisateurs.put("Sportpref1", spinner1.getSelectedItem().toString());
                Sports_utilisateurs.put("Sportpref2", spinner2.getSelectedItem().toString());
                Sports_utilisateurs.put("Sportpref3", spinner3.getSelectedItem().toString());

                documentReference.set(Sports_utilisateurs).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: choix des sports de user effectuer!!!" + userID);
                    }
                });
                startActivity(new Intent(getApplicationContext(), main_activity.class));
            }else {
                Toast.makeText(choix3Sports.this, "Veuillez selectionner exactement 3 sports   ", Toast.LENGTH_SHORT).show();
            }
        }

    });
}




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



