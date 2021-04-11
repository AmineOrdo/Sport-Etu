package com.example.sportetu.entrainement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportetu.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AdapterActivite extends FirestoreRecyclerAdapter<activite, AdapterActivite.ActiviteViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterActivite(@NonNull FirestoreRecyclerOptions<activite> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ActiviteViewHolder holder, int position, @NonNull activite activite) {

        holder.nomActivite.setText(activite.getNomActivite());
        holder.date.setText(activite.getDate());
        holder.heuredebut.setText(activite.getHeuredebut());
        holder.duree.setText(activite.getDuree());
        holder.status.setText(activite.getStatus());

    }

    @NonNull
    @Override
    public ActiviteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.entrainement_activity_row,
            parent, false);
        return new ActiviteViewHolder(v);
    }
    public void deleteItem (int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ActiviteViewHolder extends RecyclerView.ViewHolder{

        TextView nomActivite, date, heuredebut, duree, status;

        public ActiviteViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivite=itemView.findViewById(R.id.nomActivite);
            date =itemView.findViewById(R.id.textDate);
            heuredebut =itemView.findViewById(R.id.textHeure);
            duree =itemView.findViewById(R.id.dureeActivte);
            status=itemView.findViewById(R.id.status);


        }

    }
}
