package com.example.nick.petgo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    private Button boton_nuevo;
    private DatabaseReference Mascotas;
    private RecyclerView recicler;
    private Adaptador adaptador;
    private ArrayList<Mascota> extraviadas = new ArrayList<>();
    private NotificationCompat.Builder notificacion;
    private int noti = 0;

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
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                extraviadas.clear();
                for(DataSnapshot nodo: dataSnapshot.getChildren()){
                    Mascota M = nodo.getValue(Mascota.class);
                    extraviadas.add(M);
                }
                Collections.reverse(extraviadas);
                adaptador = new Adaptador(extraviadas);
                recicler.setAdapter(adaptador);

                if(!extraviadas.isEmpty()) {
                    if(noti > 0) {
                        notificacion.setSmallIcon(R.mipmap.ic_launcher);
                        notificacion.setTicker("PetGO");
                        notificacion.setWhen(System.currentTimeMillis());
                        notificacion.setContentTitle(extraviadas.get(0).getEspecie().toUpperCase() + " EXTRAVIADO");
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
        }
    }
}
