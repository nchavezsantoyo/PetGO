package com.example.nick.petgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener{
    private Button boton_cancelar;
    private Button boton_continuar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);
        boton_cancelar.setOnClickListener(this);
        boton_continuar = (Button) findViewById(R.id.boton_continuar);
        boton_continuar.setOnClickListener(this);
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

        Mascotas = FirebaseDatabase.getInstance().getReference("Mascota");
    }

    private void cancelar(){
        Intent ListSong = new Intent(getApplication(), PrincipalActivity.class);
        startActivity(ListSong);
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

        Mascota extraviadas = new Mascota(id_mascota, id_usuario, categoria, especie, sexo, tamano, edad, descripcion);
        Mascotas.child("Extraviados").child(id_mascota).setValue(extraviadas);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boton_cancelar:
                cancelar();
                break;
            case R.id.boton_continuar:
                continuar();
                break;
        }
    }
}

