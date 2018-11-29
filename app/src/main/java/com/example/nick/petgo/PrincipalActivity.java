package com.example.nick.petgo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

// Clase principal de la actividad
public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
// Declaracion de variables
    private Button boton_nuevo;
    private DatabaseReference Mascotas;
    private RecyclerView recicler;
    private AdaptadorPrincipal adaptador;
    private ArrayList<Mascota> extraviadas = new ArrayList<>();
    private NotificationCompat.Builder notificacion;
    private int noti = 0;

// Funcion al iniciar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        recicler = (RecyclerView) findViewById(R.id.recicler);
        recicler.setLayoutManager(new LinearLayoutManager(this));
        boton_nuevo = (Button) findViewById(R.id.nuevo);
        boton_nuevo.setOnClickListener(this);
        Mascotas = FirebaseDatabase.getInstance().getReference("Mascotas");
        notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);

        Mascotas.addValueEventListener(new ValueEventListener() {

// Funcion al modificar la base de datos mascotas
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                extraviadas.clear();
                for(DataSnapshot nodo: dataSnapshot.getChildren()){
                    Mascota M = nodo.getValue(Mascota.class);
                    extraviadas.add(M);
                }
                Collections.reverse(extraviadas);
                adaptador = new AdaptadorPrincipal(extraviadas);
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent int1 = new Intent(getApplicationContext(), PublicacionActivity.class);
                        int1.putExtra(PublicacionActivity.descripcion, extraviadas.get(recicler.getChildAdapterPosition(v)).getDescripcion());
                        int1.putExtra(PublicacionActivity.foto, extraviadas.get(recicler.getChildAdapterPosition(v)).getFoto());
                        int1.putExtra(PublicacionActivity.id, extraviadas.get(recicler.getChildAdapterPosition(v)).getId_mascota());
                        int1.putExtra(PublicacionActivity.user, extraviadas.get(recicler.getChildAdapterPosition(v)).getId_usuario());
                        startActivity(int1);
                    }
                });
                recicler.setAdapter(adaptador);

                if(!extraviadas.isEmpty()) {
                    if(noti > 0) {
                        notificacion.setSmallIcon(R.mipmap.ic_launcher);
                        notificacion.setTicker("PetGO");
                        notificacion.setWhen(System.currentTimeMillis());
                        notificacion.setContentTitle(extraviadas.get(0).getEspecie().toUpperCase() + " " + extraviadas.get(0).getCategoria().toUpperCase());
                        notificacion.setContentText(extraviadas.get(0).getDescripcion());
                        Intent intento = new Intent(PrincipalActivity.this, PrincipalActivity.class);
                        PendingIntent pendiente = PendingIntent.getActivity(PrincipalActivity.this, 0, intento, PendingIntent.FLAG_UPDATE_CURRENT);
                        notificacion.setContentIntent(pendiente);
                        NotificationManager N = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        N.notify(0, notificacion.build());
                    }
                    noti++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }


// Funcion que recibe las acciones de los botones
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nuevo:
                Intent int1 = new Intent(getApplicationContext(), FormularioActivity.class);
                startActivity(int1);
                break;
            case R.id.profile:
                Intent int2 = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(int2);
                break;
            case R.id.lost:
                Intent int3 = new Intent(getApplicationContext(), ExtraviadosActivity.class);
                startActivity(int3);
                break;
            case R.id.pets:
                Intent int4 = new Intent(getApplicationContext(), MascotasActivity.class);
                startActivity(int4);
                break;
            case R.id.found:
                Intent int5 = new Intent(getApplicationContext(), EncontradosActivity.class);
                startActivity(int5);
                break;
        }
    }
}
