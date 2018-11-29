package com.example.nick.petgo;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MascotasActivity extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference Mascotas;
    private RecyclerView recicler;
    private AdaptadorMiMascota adaptador;
    private ArrayList<Mascota> extraviadas = new ArrayList<>();
    private FirebaseAuth firebase;
    String mascota = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);
        firebase = FirebaseAuth.getInstance();
        recicler = (RecyclerView) findViewById(R.id.recicler);
        recicler.setLayoutManager(new LinearLayoutManager(this));
        Mascotas = FirebaseDatabase.getInstance().getReference("Mascotas");
        Mascotas.addValueEventListener(new ValueEventListener() {

            // Funcion al modificar la base de datos mascotas
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                extraviadas.clear();
                for(DataSnapshot nodo: dataSnapshot.getChildren()){
                    Mascota M = nodo.getValue(Mascota.class);
                    if(M.getId_usuario().equals(firebase.getCurrentUser().getEmail()))
                        extraviadas.add(M);
                }
                Collections.reverse(extraviadas);
                adaptador = new AdaptadorMiMascota(extraviadas);
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent int1 = new Intent(getApplicationContext(), PublicacionActivity.class);
                        int1.putExtra(PublicacionActivity.descripcion, extraviadas.get(recicler.getChildAdapterPosition(v)).getDescripcion());
                        int1.putExtra(PublicacionActivity.foto, extraviadas.get(recicler.getChildAdapterPosition(v)).getFoto());
                        int1.putExtra(PublicacionActivity.id, extraviadas.get(recicler.getChildAdapterPosition(v)).getId_mascota());
                        startActivity(int1);
                    }
                });
                recicler.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.principal:
                Intent int1 = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(int1);
                break;
            case R.id.profile:
                Intent int2 = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(int2);
                break;
            case R.id.found:
                Intent int3 = new Intent(getApplicationContext(), EncontradosActivity.class);
                startActivity(int3);
                break;
            case R.id.lost:
                Intent int4 = new Intent(getApplicationContext(), ExtraviadosActivity.class);
                startActivity(int4);
                break;

        }
    }
}
