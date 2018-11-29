package com.example.nick.petgo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorMiMascota extends RecyclerView.Adapter<AdaptadorMiMascota.VistaHolder> implements View.OnClickListener{

    ArrayList<Mascota> extraviadas;
    private View.OnClickListener listener;

    public AdaptadorMiMascota(ArrayList<Mascota> extraviadas) {
        this.extraviadas = extraviadas;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mis_mascotas, viewGroup, false);
        view.setOnClickListener(this);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.desc.setText(extraviadas.get(i).getDescripcion());
        vistaHolder.id = extraviadas.get(i).getId_mascota();
        Glide.with(vistaHolder.imagen.getContext()).load(extraviadas.get(i).getFoto()).into(vistaHolder.imagen);
        vistaHolder.setOnClickListeners();
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
        return extraviadas.size();
    }

    public class VistaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView desc;
        private ImageView imagen;
        private Button enc;
        private String id;
        Context contexto;

        public VistaHolder(View itemView) {
            super(itemView);
            contexto = itemView.getContext();
            desc = (TextView) itemView.findViewById(R.id.descripcion);
            imagen = (ImageView) itemView.findViewById(R.id.foto);
            enc = (Button) itemView.findViewById(R.id.encontrar);
        }

        void setOnClickListeners(){
            enc.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.encontrar:
                    FragmentActivity activity = (FragmentActivity)(contexto);
                    DialogFragment dialogo = new AdaptadorDialogo();
                    dialogo.show(activity.getSupportFragmentManager(),"dialogo");
                    Bundle dato = new Bundle();
                    dato.putString("mascota", id);
                    dialogo.setArguments(dato);
                    break;
            }
        }
    }

}
