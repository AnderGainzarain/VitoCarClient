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

import Model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<User> usuarios;
    private Context context;

    public UserAdapter(List<User> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.perfil_usuario,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.nombre.setText(usuarios.get(position).getNombre());
        holder.apellido.setText(usuarios.get(position).getNombre());
        holder.coche.setText(usuarios.get(position).getCoche());
        holder.telefono.setText(String.valueOf(usuarios.get(position).getTelefono()));
        holder.mail.setText(usuarios.get(position).getMail());

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView apellido;
        private TextView mail;
        private TextView telefono;
        private TextView coche;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            apellido = itemView.findViewById(R.id.apellido);
            mail = itemView.findViewById(R.id.mail);
            telefono = itemView.findViewById(R.id.telefono);
            coche = itemView.findViewById(R.id.coche);
        }
    }
}
