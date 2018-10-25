package com.example.nick.petgo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
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

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    private Button boton_nuevo;
    private Button boton_cancelar;
    private Button boton_continuar;
    private Button boton_foto;
    private RadioButton radio_perro;
    private RadioButton radio_gato;
    private RadioButton radio_macho;
    private RadioButton radio_hembra;
    private RadioButton radio_pequeno;
    private RadioButton radio_mediano;
    private RadioButton radio_grande;
    private RadioButton radio_cachorro;
    private RadioButton radio_adulto;
    private RadioButton radio_mayor;
    private EditText edit_descripcion;
    private DatabaseReference Mascotas;
    private StorageReference Almacen;
    private static final int galeria = 1;
    private ScrollView scroll_formulario;
    private ScrollView scroll_principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        boton_nuevo = (Button) findViewById(R.id.nuevo);
        boton_nuevo.setOnClickListener(this);
        boton_cancelar = (Button) findViewById(R.id.cancelar);
        boton_cancelar.setOnClickListener(this);
        boton_continuar = (Button) findViewById(R.id.continuar);
        boton_continuar.setOnClickListener(this);
        boton_foto = (Button) findViewById(R.id.foto);
        boton_foto.setOnClickListener(this);
        radio_perro = (RadioButton) findViewById(R.id.perro);
        radio_gato = (RadioButton) findViewById(R.id.gato);
        radio_macho = (RadioButton) findViewById(R.id.macho);
        radio_hembra = (RadioButton) findViewById(R.id.hembra);
        radio_pequeno = (RadioButton) findViewById(R.id.pequeno);
        radio_mediano = (RadioButton) findViewById(R.id.mediano);
        radio_grande = (RadioButton) findViewById(R.id.grande);
        radio_cachorro = (RadioButton) findViewById(R.id.cachorro);
        radio_adulto = (RadioButton) findViewById(R.id.adulto);
        radio_mayor = (RadioButton) findViewById(R.id.mayor);
        edit_descripcion = (EditText) findViewById(R.id.descripcion);
        scroll_formulario = (ScrollView) findViewById(R.id.formulario);
        scroll_principal = (ScrollView) findViewById(R.id.principal);

        Mascotas = FirebaseDatabase.getInstance().getReference("Mascota");
        Almacen = FirebaseStorage.getInstance().getReference();
    }

    private void continuar(){
        String id_mascota = Mascotas.push().getKey();
        String id_usuario = "1";
        String categoria = "Extraviado";
        String especie = "";
        String sexo = "";
        String tamano = "";
        String edad = "";
        String descripcion = edit_descripcion.getText().toString();

        if(radio_perro.isChecked())
            especie = "Perro";
        else
            especie = "Gato";
        if(radio_macho.isChecked())
            sexo = "Macho";
        else
            sexo = "Hembra";
        if(radio_pequeno.isChecked())
            tamano = "Pequeño";
        if(radio_mediano.isChecked())
            tamano = "Mediano";
        if(radio_grande.isChecked())
            tamano = "Grande";
        if(radio_cachorro.isChecked())
            edad = "Cachorro";
        if(radio_adulto.isChecked())
            edad = "Adulto";
        if(radio_grande.isChecked())
            edad = "Mayor";

        if(especie.equals("") || sexo.equals("") || tamano.equals("") || edad.equals("") || descripcion.equals(""))
            Toast.makeText(PrincipalActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        else {
            Mascota extraviadas = new Mascota(id_mascota, id_usuario, categoria, especie, sexo, tamano, edad, descripcion);
            Mascotas.child("Extraviados").child(id_mascota).setValue(extraviadas);

            cancelar();
            Toast.makeText(PrincipalActivity.this, "OK", Toast.LENGTH_SHORT).show();

            edit_descripcion.setText("");
            radio_perro.setChecked(false);
            radio_gato.setChecked(false);
            radio_cachorro.setChecked(false);
            radio_adulto.setChecked(false);
            radio_mayor.setChecked(false);
            radio_macho.setChecked(false);
            radio_hembra.setChecked(false);
            radio_pequeno.setChecked(false);
            radio_mediano.setChecked(false);
            radio_grande.setChecked(false);
        }
    }

    public void camara(){
        Intent abrir = new Intent(Intent.ACTION_PICK);
        abrir.setType("image/*");
        startActivityForResult(abrir, galeria);
    }

    public void cancelar(){
        scroll_formulario.setVisibility(View.INVISIBLE);
        scroll_principal.setVisibility(View.VISIBLE);
        boton_nuevo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference direccion = Almacen.child("Mascotas").child(uri.getLastPathSegment());
            direccion.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PrincipalActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelar:
                cancelar();
                break;
            case R.id.continuar:
                continuar();
                break;
            case R.id.foto:
                camara();
                break;
            case R.id.nuevo:
                scroll_formulario.setVisibility(View.VISIBLE);
                scroll_principal.setVisibility(View.INVISIBLE);
                boton_nuevo.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
