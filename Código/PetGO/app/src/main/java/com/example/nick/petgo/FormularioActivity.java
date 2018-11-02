package com.example.nick.petgo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener {
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
    private EditText edit_descripcion;
    private TextView view_fecha;
    private DatabaseReference Mascotas;
    private StorageReference Almacen;
    private static final int galeria = 1;
    private ProgressDialog cargando;
    private String fecha = "";
    private String id_mascota = "";
    private String url = "ola";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
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
        edit_descripcion = (EditText) findViewById(R.id.descripcion);
        view_fecha = (TextView) findViewById(R.id.fecha);
        view_fecha.setOnClickListener(this);
        Mascotas = FirebaseDatabase.getInstance().getReference("Mascotas");
        Almacen = FirebaseStorage.getInstance().getReference();
        cargando = new ProgressDialog(this);
        cargando.setCancelable(false);
    }

    private void fecha(){
        final Calendar calendario = Calendar.getInstance();
        DatePickerDialog datepicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                view_fecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                fecha = dayOfMonth + "/" + (month+1) + "/" + year;
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        datepicker.show();

    }

    private void continuar(){

        String id_usuario = "1";
        String categoria = "Extraviado";
        String especie = "";
        String sexo = "";
        String tamano = "";
        String descripcion = edit_descripcion.getText().toString();

        if(radio_perro.isChecked())
            especie = "Perro";
        if(radio_gato.isChecked())
            especie = "Gato";
        if(radio_macho.isChecked())
            sexo = "Macho";
        if(radio_hembra.isChecked())
            sexo = "Hembra";
        if(radio_pequeno.isChecked())
            tamano = "Pequeño";
        if(radio_mediano.isChecked())
            tamano = "Mediano";
        if(radio_grande.isChecked())
            tamano = "Grande";


        if(especie.equals("") || sexo.equals("") || tamano.equals("") || descripcion.equals("") || fecha.equals("") || id_mascota.equals(""))
            Toast.makeText(FormularioActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        else {
            Mascota extraviadas = new Mascota(id_mascota, id_usuario, categoria, especie, sexo, tamano, descripcion, fecha, url);
            Mascotas.child(id_mascota).setValue(extraviadas);

            Toast.makeText(FormularioActivity.this, "OK", Toast.LENGTH_SHORT).show();
            cancelar();
        }
    }

    public void galeria(){
        Intent abrir = new Intent(Intent.ACTION_PICK);
        abrir.setType("image/*");
        startActivityForResult(abrir, galeria);
    }

    public void cancelar(){
        Intent ListSong = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(ListSong);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            cargando.setMessage("Cargando imagen...");
            cargando.show();
            Uri uri = data.getData();
            id_mascota = Mascotas.push().getKey();
            final StorageReference direccion = Almacen.child("Mascotas").child(id_mascota);
            direccion.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    cargando.dismiss();
                    // obtener direccion de imagen
                    direccion.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                        }
                    });
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
                galeria();
                break;
            case R.id.fecha:
                fecha();
                break;
        }
    }
}
