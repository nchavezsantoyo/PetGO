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
import com.google.firebase.storage.StorageReference;

public class AdaptadorMensaje extends DialogFragment {
    private DatabaseReference Mensajes;
    private FirebaseAuth firebase;
    private EditText edit_mensaje;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.item_mensaje, null);
        edit_mensaje = view.findViewById(R.id.msn);
        Mensajes = FirebaseDatabase.getInstance().getReference("Mensajes");
        firebase = FirebaseAuth.getInstance();

        builder.setView(view)
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id_usuario = firebase.getCurrentUser().getEmail();
                        String id_mensaje = Mensajes.push().getKey();
                        String mensaje = edit_mensaje.getText().toString();
                        String destino = getArguments().getString("user");;
                        Mensaje mensajes = new Mensaje(id_mensaje, id_usuario, mensaje, destino);
                        Mensajes.child(id_mensaje).setValue(mensajes);
                        Toast.makeText(getContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}


