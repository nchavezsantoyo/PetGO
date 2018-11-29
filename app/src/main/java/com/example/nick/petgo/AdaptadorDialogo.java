package com.example.nick.petgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaptadorDialogo extends DialogFragment {
    private DatabaseReference Mascotas;
    private FirebaseAuth firebase;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Mascotas = FirebaseDatabase.getInstance().getReference("Mascotas");
        firebase = FirebaseAuth.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Encontraste a su dueño?").
        setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat c = new SimpleDateFormat("dd/MM/yyy");
                Mascotas.child(getArguments().getString("mascota")).child("categoria").setValue("Encontrado");
                Mascotas.child(getArguments().getString("mascota")).child("fecha").setValue(c.format(new Date()));
            }
        }).
        setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}


