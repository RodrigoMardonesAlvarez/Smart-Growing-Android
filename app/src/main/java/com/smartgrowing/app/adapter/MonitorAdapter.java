package com.smartgrowing.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smartgrowing.app.R;
import com.smartgrowing.app.VincularPlaca;
import com.smartgrowing.app.model.Monitor;

public class MonitorAdapter extends FirestoreRecyclerAdapter<Monitor, MonitorAdapter.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MonitorAdapter(@NonNull FirestoreRecyclerOptions<Monitor> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Monitor model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();
        holder.alias.setText(model.getAlias());
        holder.temp.setText(model.getTemp());
        holder.acid.setText(model.getAcid());

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, VincularPlaca.class);
                i.putExtra("id_placa",id);
                activity.startActivity(i);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_monitor_single,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alias, temp, acid;
        Button btn_edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alias = itemView.findViewById(R.id.alias);
            temp = itemView.findViewById(R.id.temp);
            acid = itemView.findViewById(R.id.acid);
            btn_edit = itemView.findViewById(R.id.btn_edit);

        }
    }

}
