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

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextName;
    private EditText TextLastname;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        firebaseAuth = FirebaseAuth.getInstance();
        TextEmail = (EditText) findViewById(R.id.correo);
        TextPassword = (EditText) findViewById(R.id.contrasena);
        TextName = (EditText) findViewById(R.id.date);
        TextLastname = (EditText) findViewById(R.id.apellido);
        btnRegistrar = (Button) findViewById(R.id.registrar);
        progressDialog = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(this);
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
    }

    private void registrar(){
        final String email = TextEmail.getText().toString().trim();
        final String password  = TextPassword.getText().toString().trim();
        final String name = TextName.getText().toString().trim();
        final String lastname  = TextLastname.getText().toString().trim();
        final String id_usuario = Usuarios.push().getKey();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            Toast.makeText(this,"Se debe ingresar un apellido",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Se debe ingresar un nombre",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this,"La contraseña debe ser mayor a 6 dígitos",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Usuario usuarios = new Usuario(id_usuario, name, lastname, email, password);
                            Usuarios.child(id_usuario).setValue(usuarios);
                            Toast.makeText(RegistrarActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(RegistrarActivity.this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(RegistrarActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String user =  TextName.getText().toString() +  TextLastname.getText().toString();
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrarActivity.this, "Bienvenido: " + user, Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), PerfilActivity.class);
                            intencion.putExtra(PerfilActivity.user, user);
                            startActivity(intencion);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        registrar();
    }
}