package com.ander.vitocarclient.Controller.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ander.vitocarclient.Controller.RvInterface;
import com.ander.vitocarclient.R;

import java.util.List;

import com.ander.vitocarclient.Model.Viaje;

public class ViajeAdapter extends RecyclerView.Adapter<ViajeAdapter.ViewHolder>{
    private final RvInterface rvInterface;
    private List<Viaje> viajes;

    public ViajeAdapter(List<Viaje> viajes, RvInterface rvInterface) {
        this.viajes = viajes;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viaje,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String precio =viajes.get(position).getPrecioString()+ "€";
        holder.origen.setText(viajes.get(position).getOrigen());
        holder.destino.setText(viajes.get(position).getDestino());
        holder.fecha.setText(viajes.get(position).getFechaSalida());
        holder.precio.setText(precio);
    }

    @Override
    public int getItemCount() {
        return viajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView origen;
        private TextView destino;
        private TextView fecha;
        private TextView precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            origen = itemView.findViewById(R.id.origen);
            destino = itemView.findViewById(R.id.destino);
            fecha = itemView.findViewById(R.id.fecha);
            precio = itemView.findViewById(R.id.precio);

            itemView.setOnClickListener(view -> {
                if(rvInterface != null){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        rvInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
