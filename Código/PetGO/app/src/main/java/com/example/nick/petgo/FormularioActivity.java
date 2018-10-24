package com.example.nick.petgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener{
    private Button boton_cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);
        boton_cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent ListSong = new Intent(getApplication(), PrincipalActivity.class);
        startActivity(ListSong);
    }
}

