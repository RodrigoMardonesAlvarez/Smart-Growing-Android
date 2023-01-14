package com.smartgrowing.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VincularPlaca extends AppCompatActivity {

    Button btn_add;
    EditText cultivo;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_placa);

        this.setTitle("Sincronizar placa activa");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra("id_placa");
        mFirestore = FirebaseFirestore.getInstance();

        cultivo = findViewById(R.id.cultivo);
        btn_add = findViewById(R.id.btn_add);


        if (id == null || id == ""){
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namecultivo = cultivo.getText().toString().trim();

                    if (namecultivo.isEmpty()){
                        Toast.makeText(VincularPlaca.this, "Debe completar ambos campos", Toast.LENGTH_SHORT).show();
                    }else{
                        sincronizarDispositivo(namecultivo);
                    }
                }
            });
        }else{
            btn_add.setText("Guardar cambios");
            getDispositivo(id);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String namecultivo = cultivo.getText().toString().trim();

                    if (namecultivo.isEmpty()){
                        Toast.makeText(VincularPlaca.this, "Debes darle un nombre a tu cultivo", Toast.LENGTH_SHORT).show();
                    }else{
                        updateDispositivo(namecultivo,id);
                    }

                }
            });

        }

    }

    private void updateDispositivo(String namecultivo, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("alias", namecultivo);

        mFirestore.collection("dispositivos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(VincularPlaca.this, "Datos actualizados satisfactoriamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VincularPlaca.this, "Ha ocurrido un error al configurar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sincronizarDispositivo(String namecultivo) {
        Map<String, Object> map = new HashMap<>();
        map.put("alias", namecultivo);


        mFirestore.collection("dispositivos").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(VincularPlaca.this, "Cultivo sincronizado con éxito", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VincularPlaca.this, "Ha ocurrido un problema", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDispositivo(String id){
        mFirestore.collection("dispositivos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String aliasMon = documentSnapshot.getString("alias");
                cultivo.setText(aliasMon);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VincularPlaca.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}