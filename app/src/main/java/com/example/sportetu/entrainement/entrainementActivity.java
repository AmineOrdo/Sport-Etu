package com.example.sportetu.entrainement;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportetu.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class entrainementActivity extends AppCompatActivity {




    private AdapterActivite adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrainement);


        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        String userID;
        userID= mAuth.getCurrentUser().getUid();


        RecyclerView recyclerView =findViewById(R.id.RecyclerView_activite_plan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection(userID).whereEqualTo("status", "planifie");
        //Query query = db.collection(userID).orderBy("date", Query.Direction.ASCENDING).orderBy("duree", Query.Direction.ASCENDING).orderBy("status", Query.Direction.ASCENDING);
        // Query query = db.collection("test").orderBy("date", Query.Direction.ASCENDING);

         FirestoreRecyclerOptions<activite> options= new FirestoreRecyclerOptions.Builder<activite>()
                .setQuery(query, activite.class)
                .build();

        adapter=new AdapterActivite(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }


}
