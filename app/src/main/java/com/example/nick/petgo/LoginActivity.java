package com.example.nick.petgo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);
        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.botonLogin);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void crearcuenta() {
        Intent intencion = new Intent(getApplication(), RegistrarActivity.class);
        startActivity(intencion);
    }

    private void loguear() {
        final String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intencion = new Intent(getApplication(), PrincipalActivity.class);
                            startActivity(intencion);
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Email o contraseña incorrectos ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botonRegistrar:
                crearcuenta();
                break;
            case R.id.botonLogin:
                loguear();
                break;
        }
    }
}