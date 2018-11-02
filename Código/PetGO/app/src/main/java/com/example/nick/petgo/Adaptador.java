package com.example.nick.petgo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.VistaHolder>{

    ArrayList<Mascota> extraviadas;

    public Adaptador(ArrayList<Mascota> extraviadas) {
        this.extraviadas = extraviadas;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vista, viewGroup, false);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.desc.setText(extraviadas.get(i).getDescripcion());
        vistaHolder.id.setText(extraviadas.get(i).getId_mascota());
        Glide.with(vistaHolder.imagen.getContext()).load(extraviadas.get(i).getFoto()).into(vistaHolder.imagen);
    }

    @Override
    public int getItemCount() {
        return extraviadas.size();
    }

    public class VistaHolder extends RecyclerView.ViewHolder {
        private TextView desc, id;
        private ImageView imagen;

        public VistaHolder(View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.desc);
            id = (TextView) itemView.findViewById(R.id.id);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
        }

    }

}
