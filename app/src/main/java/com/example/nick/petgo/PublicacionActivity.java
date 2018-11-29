package com.example.nick.petgo;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// Clase pincipal de la actividad
public class PublicacionActivity extends AppCompatActivity implements View.OnClickListener {
// Declaracion de variables
    public static final String foto = "foto";
    public static final String descripcion = "descripcion";
    public static final String id = "id";
    public static final String user = "user";
    TextView text_descripcion;
    ImageView image_foto;

    private Button mensaje;
    private EditText edit_comentar;
    private ImageButton boton_enviar;
    private DatabaseReference Comentarios;
    private RecyclerView recicler;
    private AdaptadorComentario adaptador;
    private ArrayList<Comentario> comentarios = new ArrayList<>();
    private FirebaseAuth firebase;
    private String id_mascota = "";

// Funcion al iniciar la actividad realiza las referencias a las variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        mensaje = (Button)findViewById(R.id.mensaje);
        image_foto =(ImageView)findViewById(R.id.foto);
        text_descripcion =(TextView)findViewById(R.id.descripcion);
        edit_comentar =(EditText) findViewById(R.id.comentar);
        Glide.with(this).load(getIntent().getStringExtra("foto")).into(image_foto);
        text_descripcion.setText(getIntent().getStringExtra("descripcion"));

        recicler = (RecyclerView) findViewById(R.id.recicler);
        recicler.setLayoutManager(new LinearLayoutManager(this));
        boton_enviar = (ImageButton) findViewById(R.id.enviar);
        boton_enviar.setOnClickListener(this);
        Comentarios = FirebaseDatabase.getInstance().getReference("Comentarios");
        firebase = FirebaseAuth.getInstance();

        id_mascota = getIntent().getStringExtra("id");
        Comentarios.addValueEventListener(new ValueEventListener() {
// Funcion al momento de un cambio de datos a la Base de datos en comentarios
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comentarios.clear();
                for(DataSnapshot nodo: dataSnapshot.getChildren()){
                    Comentario C = nodo.getValue(Comentario.class);
                    if(id_mascota.equals(C.getId_mascota()))
                        comentarios.add(C);
                }
                adaptador = new AdaptadorComentario(comentarios);
                recicler.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

// Funcion que realiza el envio de un comentario a la base de datos
    private void enviar() {
        String id_comentario = Comentarios.push().getKey();
        String id_usuario = firebase.getCurrentUser().getEmail();
        String contenido = edit_comentar.getText().toString().trim();

        SimpleDateFormat c = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        String fecha = "Escrito el "+ c.format(new Date());

        if (!TextUtils.isEmpty(contenido)) {
            Comentario comentarios = new Comentario(id_comentario, id_mascota, contenido, id_usuario, fecha);
            Comentarios.child(id_comentario).setValue(comentarios);
        }
        edit_comentar.setText(null);
        edit_comentar.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

// Funcion que recibe las acciones de los botones
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enviar:
                enviar();
                break;
            case R.id.mensaje:
                DialogFragment dialogo = new AdaptadorMensaje();
                dialogo.show(getSupportFragmentManager(),"dialogo");
                Bundle dato = new Bundle();
                dato.putString("user", getIntent().getStringExtra("user"));
                dialogo.setArguments(dato);
                break;
        }
    }
}
