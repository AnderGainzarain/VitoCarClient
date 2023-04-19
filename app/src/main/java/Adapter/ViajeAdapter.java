package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ander.vitocarclient.R;

import java.util.List;

import Model.Viaje;

public class ViajeAdapter extends RecyclerView.Adapter<ViajeAdapter.ViewHolder>{
    private List<Viaje> viajes;
    private Context context;

    public ViajeAdapter(List<Viaje> viajes, Context context) {
        this.viajes = viajes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viaje,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String origen =viajes.get(position).getOrigen();
        String destino =viajes.get(position).getDestino();
        String fecha =viajes.get(position).getFechaSalida();
        String precio =viajes.get(position).getPrecioString()+ "€";
        holder.origen.setText(origen);
        holder.destino.setText(destino);
        holder.fecha.setText(fecha);
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

        }
    }
}
