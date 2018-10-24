package com.example.nick.petgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    private Button boton_nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        boton_nuevo = (Button) findViewById(R.id.boton_nuevo);
        boton_nuevo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent ListSong = new Intent(getApplication(), FormularioActivity.class);
        startActivity(ListSong);
    }
}
