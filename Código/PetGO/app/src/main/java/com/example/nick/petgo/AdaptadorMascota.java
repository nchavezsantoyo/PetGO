package com.example.nick.petgo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorMascota extends RecyclerView.Adapter<AdaptadorMascota.VistaHolder> implements View.OnClickListener{

    ArrayList<Mascota> extraviadas;
    private View.OnClickListener listener;

    public AdaptadorMascota(ArrayList<Mascota> extraviadas) {
        this.extraviadas = extraviadas;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mascota, viewGroup, false);
        view.setOnClickListener(this);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.date.setText(extraviadas.get(i).getCategoria() + " el " + extraviadas.get(i).getFecha());
        Glide.with(vistaHolder.imagen.getContext()).load(extraviadas.get(i).getFoto()).into(vistaHolder.imagen);
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

    public class VistaHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView imagen;

        public VistaHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            imagen = (ImageView) itemView.findViewById(R.id.foto);
        }
    }

}
