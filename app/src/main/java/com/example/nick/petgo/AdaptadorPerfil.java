package com.example.nick.petgo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPerfil extends RecyclerView.Adapter<AdaptadorPerfil.VistaHolder> implements View.OnClickListener{

    ArrayList<Mensaje> mensajes;
    private View.OnClickListener listener;

    public AdaptadorPerfil(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_perfil, viewGroup, false);
        view.setOnClickListener(this);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.mensaje.setText(mensajes.get(i).mensaje);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class VistaHolder extends RecyclerView.ViewHolder {
        private TextView mensaje;

        public VistaHolder(View itemView) {
            super(itemView);
            mensaje = (TextView) itemView.findViewById(R.id.mensaje);
        }
    }

}
