package com.example.nick.petgo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorPrincipal extends RecyclerView.Adapter<AdaptadorPrincipal.VistaHolder> implements View.OnClickListener{

    ArrayList<Mascota> extraviadas;
    private View.OnClickListener listener;

    public AdaptadorPrincipal(ArrayList<Mascota> extraviadas) {
        this.extraviadas = extraviadas;
    }

    @Override
    public VistaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vista, viewGroup, false);
        view.setOnClickListener(this);
        return new VistaHolder(view);
    }

    @Override
    public void onBindViewHolder(VistaHolder vistaHolder, int i) {
        vistaHolder.desc.setText(extraviadas.get(i).getDescripcion());
        vistaHolder.date.setText("Encontrado el " + extraviadas.get(i).getFecha());
        vistaHolder.info.setText(extraviadas.get(i).getEspecie().toUpperCase() + " " + extraviadas.get(i).getSexo().toUpperCase() + " "
                + extraviadas.get(i).getTamano().toUpperCase());
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
        private TextView desc, date, info;
        private ImageView imagen;

        public VistaHolder(View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.desc);
            date = (TextView) itemView.findViewById(R.id.user);
            info = (TextView) itemView.findViewById(R.id.info);
            imagen = (ImageView) itemView.findViewById(R.id.foto);
        }

    }

}
