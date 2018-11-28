package com.example.nick.petgo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtUser;
    private DatabaseReference Mensajes;
    private RecyclerView recicler;
    private AdaptadorPerfil adaptador;
    private ArrayList<Mensaje> mensajes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtUser =(TextView)findViewById(R.id.TextUser);
        txtUser.setText("¡Bienvenido!");
        Mensajes = FirebaseDatabase.getInstance().getReference("Mensajes");
        recicler = (RecyclerView) findViewById(R.id.recicler);
        recicler.setLayoutManager(new LinearLayoutManager(this));

        Mensajes.addValueEventListener(new ValueEventListener() {

            // Funcion al modificar la base de datos mascotas
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensajes.clear();
                for(DataSnapshot nodo: dataSnapshot.getChildren()){
                    Mensaje M = nodo.getValue(Mensaje.class);
                    mensajes.add(M);
                }
                Collections.reverse(mensajes);
                adaptador = new AdaptadorPerfil(mensajes);
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                recicler.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

            @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.principal:
                Intent int1 = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(int1);
                break;
            case R.id.lost:
                Intent int2 = new Intent(getApplicationContext(), ExtraviadosActivity.class);
                startActivity(int2);
                break;
            case R.id.found:
                Intent int3 = new Intent(getApplicationContext(), EncontradosActivity.class);
                startActivity(int3);
                break;
        }
    }
}