package com.example.nick.petgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String user = "names";
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        txtUser =(TextView)findViewById(R.id.TextUser);
        String user = getIntent().getStringExtra("names");
        txtUser.setText("¡Bienvenido "+ user +"!");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.principal:
                Intent int1 = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(int1);
                break;
        }
    }
}