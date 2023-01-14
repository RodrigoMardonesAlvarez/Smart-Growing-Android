package com.smartgrowing.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.smartgrowing.app.adapter.MonitorAdapter;
import com.smartgrowing.app.model.Monitor;

public class MainActivity extends AppCompatActivity {

    Button btn_exit, btn_add;
    RecyclerView mRecycler;
    MonitorAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingle);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("dispositivos");

        FirestoreRecyclerOptions<Monitor> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Monitor>().setQuery(query,Monitor.class).build();

        mAdapter = new MonitorAdapter(firestoreRecyclerOptions, this);

        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        btn_add = findViewById(R.id.btn_add);
        btn_exit = findViewById(R.id.btn_close);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,VincularPlaca.class));
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                mAuth.signOut();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

}