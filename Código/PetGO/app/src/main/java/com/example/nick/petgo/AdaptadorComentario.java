package com.example.nick.petgo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorComentario extends RecyclerView.Adapter<AdaptadorComentario.VistaHolder>{

    ArrayList<Comentario> comentarios;

    public AdaptadorComentario(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comentario, viewGroup, false);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.date.setText(comentarios.get(i).getFecha());
        vistaHolder.info.setText(comentarios.get(i).getContenido());
        vistaHolder.user.setText(comentarios.get(i).getContenido());
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class VistaHolder extends RecyclerView.ViewHolder {
        private TextView info, date, user;

        public VistaHolder(View itemView) {
            super(itemView);
            info = (TextView) itemView.findViewById(R.id.info);
            user = (TextView) itemView.findViewById(R.id.user);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
